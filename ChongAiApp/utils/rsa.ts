import JSEncrypt from 'jsencrypt'
import {
	request
} from '@/utils/request'
// 从后端获取公钥的API
export async function getPublicKey() {

	const {data:publicKey} = (await request<Response<string>>({
		url: '/public/publicKey',
		method: 'GET'
	})).data

	return publicKey;
}

// RSA加密方法
export function encryptWithRSA(publicKey:string, text:string) {
	const encryptor = new JSEncrypt()
	encryptor.setPublicKey(publicKey)
	return encryptor.encrypt(text)
}