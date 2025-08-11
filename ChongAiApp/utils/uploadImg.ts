import { useUserStore } from "@/stores/user";
import { Env } from "./env";
//@TODO refactor
// export function uploadImg(avatarCallbackFn:(avatarCallbackData: AvatarCallbackData)=>void) {
// 	const URL = Env.VITE_API_BASE_URL
// 	const userStore = useUserStore();
// 	const token = userStore.token;

// 	// 检查token是否存在，如果不存在则使用空字符串
// 	// const authToken = token || '';

// 	uni.chooseImage({
// 		count: 1,
// 		success: async (chooseImageRes) => {
// 			  uni.showLoading({
// 			        title: '上传中...',
// 			        mask: true
// 			      });
// 			try {
// 				const uploadRes = await uni.uploadFile({
// 					url: `${URL}/public/app/upload/avatar`,
// 					filePath: chooseImageRes.tempFilePaths[0],
// 					name: 'avatarfile',
// 					formData: {
// 						user: 'test'
// 					},
// 					header: {
// 						Authorization: `Bearer ${token}`
// 					}
// 				});
// 				console.log(chooseImageRes.tempFilePaths[0]);
// 				const response = JSON.parse(uploadRes.data);
// 				avatarCallbackFn?.({
// 					relativePath: response.imgUrl,
// 					fullUrl: chooseImageRes.tempFilePaths[0]
// 					// fullUrl: URL + response.imgUrl
// 				});

// 				uni.showToast({
// 					title: '上传成功',
// 					icon: 'success'
// 				});

// 			} catch (error) {
// 				console.error('上传处理异常:', error);
// 				uni.showToast({
// 					title: error.message || '上传失败',
// 					icon: 'none'
// 				});
// 			} finally {
// 				uni.hideLoading(); // 确保无论成功失败都关闭loading
// 			}
// 		},
// 		fail: (error) => {
// 			uni.hideLoading(); // 选择图片失败也关闭loading
// 			console.error('选择图片失败:', error);
// 			uni.showToast({
// 				title: '选择图片失败',
// 				icon: 'none'
// 			});
// 		}
// 	});
// }
export function uploadImg<T>(url: string, filePath: string, fileKey: string) {
  const BASE_URL = 'https://122.228.237.118:53627';
  const userStore = useUserStore();
  const token = userStore.token;

  return new Promise<Response<T>>(
    (resolve, reject: (reason: ErrorResponse) => void) => {
      uni.uploadFile({
        url: BASE_URL + url,
        filePath: filePath,
        name: fileKey,
		method:'POST',
        header: {
          Authorization: `Bearer ${token}`,
        },
        success: (res) => {
          //visual studio code compat
          //@ts-ignore5
          const response: Response<T> = res.data;
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
