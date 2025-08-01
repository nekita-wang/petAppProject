import { defineStore } from 'pinia'
import { ref } from 'vue'

interface UserData {
	token?: string
	userId?: string
	phone?: string
}

export const useUserStore = defineStore('userStore', () => {
	const token = ref<string>('')
	const userId = ref<string>('')
	const phone = ref<string>('')

	// 设置用户信息
	const setUserInfo = (userData: UserData): void => {
		token.value = userData.token || ''
		userId.value = userData.userId || ''
		phone.value = userData.phone || ''

		// 持久化存储
		uni.setStorageSync('userInfo', JSON.stringify({
			token: token.value,
			userId: userId.value,
			phone: phone.value,
		}))
	}

	// 清除用户信息
	const clearUserInfo = (): void => {
		token.value = ''
		userId.value = ''
		phone.value = ''
		uni.removeStorageSync('userInfo')
	}

	return {
		token,
		userId,
		phone,
		setUserInfo,
		clearUserInfo
	}
}) 