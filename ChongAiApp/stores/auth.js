import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const token = ref('')
  const userId = ref('')
  const phone = ref('')
  
  // 设置用户信息
  const setUserInfo = (userData) => {
    token.value = userData.token || ''
    userId.value = userData.userId || ''
    phone.value = userData.phone || ''
    
    // 持久化存储
    uni.setStorageSync('authInfo', JSON.stringify({
      token: token.value,
      userId: userId.value,
      phone: phone.value
    }))
  }
  
  // 清除用户信息
  const clearUserInfo = () => {
    token.value = ''
    userId.value = ''
    phone.value = ''
    uni.removeStorageSync('authInfo')
  }

  return { token, userId, phone, setUserInfo, clearUserInfo }
})