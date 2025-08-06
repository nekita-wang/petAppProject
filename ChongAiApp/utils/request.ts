// utils/request.js
import { Env } from "./env";
import { useUserStore } from "@/stores/user";
// const BASE_URL = 'https://122.228.237.118:53627'
// when its dev enable debug mode
const ENABLE_DEBUG = Env.VITE_USER_NODE_ENV === "development";
// const BASE_URL = Env.VITE_API_BASE_URL; //服务器
const BASE_URL = 'https://122.228.237.118:53627'; // 生产地址
const TIMEOUT = 5000;
type RequestConfig = {
	url : string;
	method ?: "GET" | "POST";
	data ?: any;
	header ?: Record<string, string>;
};

export const request = <T>(config : RequestConfig) => {
	if (!config) {
		console.error("参数错误");
		return Promise.reject(new Error("参数错误"));
	}
	const { url, method = "GET", data, header } = config;
	const token = useUserStore().token;
	const allHeader = {
		"Content-Type": "application/json",
		...(token && { Authorization: `Bearer ${token}` }),
		"ngrok-skip-browser-warning": "true",
		...header,
	};
	return new Promise<Response<T>>((resolve, reject:(reason: ErrorResponse) => void) => {
		uni.request({
			url: BASE_URL + url,
			method,
			data: data,
			header: allHeader,
			success: (res) => {
				//visual studio code compat
				//@ts-ignore
				const response : Response<T> = res.data;
				if (response.success) {
					resolve(response);
					return;
				}
				const err = _response2ErrorMsg(response);
				// console.log(err);
				uni.showToast({ title: err.msg, icon: "none" });
				reject(err);
			},
			fail: (err) => {
				uni.showToast({ title: "网络连接失败", icon: "none" });
				reject({
					code: -1,
					msg: "网络错误",
					error: err,
				});
			},
		});
	}).catch((err) => {
		if (err.statusCode === 401) {
			uni.navigateTo({ url: "/pages/login/login" });
		}
		throw err; // 继续抛出错误
	});
};
const _response2ErrorMsg = (res : Response<any>) : ErrorResponse => ({
	code: res.code,
	msg: res.msg || "请求失败",
	error: res.data,
});