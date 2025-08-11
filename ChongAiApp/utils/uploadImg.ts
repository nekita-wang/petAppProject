import { useUserStore } from "@/stores/user";
import { Env } from "./env";
export function uploadImg<T>(url : string, filePath : string, fileKey : string) {
	const BASE_URL = Env.VITE_API_BASE_URL;
	const userStore = useUserStore();
	const token = userStore.token;

	return new Promise<Response<T>>(
		(resolve, reject : (reason : ErrorResponse) => void) => {
			uni.uploadFile({
				url: BASE_URL + url,
				filePath: filePath,
				name: fileKey,
				method: 'POST',
				header: {
					Authorization: `Bearer ${token}`,
				},
				success: (res) => {
					//visual studio code compat
					//@ts-ignore
					const response : Response<T> = res.data;
					resolve(response);
					uni.showToast({
						title: "上传成功",
						icon: "success",
					});
				},
				fail: (err) => {
					console.error(err);
					reject({
						code: -1,
						msg: "网络错误",
						error: err,
					});
				},
			});
		}
	);
}