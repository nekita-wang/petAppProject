import JSEncrypt from 'jsencrypt'
import {
	request
} from '@/utils/request.js'
// 从后端获取公钥的API
export async function getPublicKey() {

	const res = await request({
		url: '/publicKey',
		method: 'GET',
		header: {
			'ngrok-skip-browser-warning': 'true' //测试 添加请求头绕过ngrok拦截
		}
	})
	// 调试输出完整响应
	console.log('公钥接口完整响应:', res.publicKey)
	return res.publicKey
}

// RSA加密方法
export function encryptWithRSA(publicKey, text) {
	const encryptor = new JSEncrypt()
	encryptor.setPublicKey(publicKey)
	return encryptor.encrypt(text)
}