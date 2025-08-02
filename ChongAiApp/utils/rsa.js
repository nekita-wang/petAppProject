import JSEncrypt from 'jsencrypt'
import {
	request
} from '@/utils/request'
// 从后端获取公钥的API
export async function getPublicKey() {

	const res = await request({
		url: '/public/publicKey',
		method: 'GET'
	})

	return res.publicKey
}

// RSA加密方法
export function encryptWithRSA(publicKey, text) {
	const encryptor = new JSEncrypt()
	encryptor.setPublicKey(publicKey)
	return encryptor.encrypt(text)
}