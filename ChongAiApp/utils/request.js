// utils/request.js
import {
	useAuthStore
} from '@/stores/auth'

// const BASE_URL = 'http://115.120.195.253' //服务器
const BASE_URL = 'http://1.15.123.85' //服务器

export const request = (config = {}) => {
	const {
		url,
		method = "GET",
		data = {},
		header = {}
	} = config

	// 获取认证 token
	const authStore = useAuthStore()
	const token = authStore.token

	// 构建完整 URL（处理 GET 参数）
	let fullUrl = BASE_URL + url
	if (method === 'GET' && Object.keys(data).length > 0) {
		const params = new URLSearchParams(data).toString()
		fullUrl += `?${params}`
	}

	// 构建请求头
	const headers = {
		'Content-Type': 'application/json',
		...(token ? {
			'Authorization': `Bearer ${token}`
		} : {}),
		'ngrok-skip-browser-warning': 'true', // 添加绕过 ngrok 的头部 ---测试
		...header
	}

	return new Promise((resolve, reject) => {
		uni.request({
			url: fullUrl,
			method,
			data: method === 'GET' ? undefined : data,
			header: headers,
			success: (res) => {
				if (res.statusCode >= 200 && res.statusCode < 300) {
					resolve(res.data)
				} else {
					reject({
						statusCode: res.statusCode,
						message: res.data?.message || `请求失败: ${res.statusCode}`,
						data: res.data
					})
				}
			},
			fail: (err) => {
				console.error(`[Request Failed] ${method} ${fullUrl}`, err)
				reject({
					statusCode: -1,
					message: '网络连接失败',
					error: err
				})
			}
		})
	})
}