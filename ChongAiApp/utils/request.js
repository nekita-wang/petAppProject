// utils/request.js
import { useAuthStore } from '@/stores/auth'

// const BASE_URL = 'https://122.228.237.118:53627' 
const BASE_URL = 'http://1.15.123.85' //服务器

export const request = (config = {}) => {
  const { url, method = "GET", data = {}, header = {} } = config
  const token = useAuthStore().token
  let fullUrl = BASE_URL + url

  if (method === 'GET' && Object.keys(data).length) {
    fullUrl += `?${new URLSearchParams(data)}`
  }

  return new Promise((resolve, reject) => {
    uni.request({
      url: fullUrl,
      method,
      data: method === 'GET' ? undefined : data,
      header: {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` }),
        'ngrok-skip-browser-warning': 'true',
        ...header
      },
      success: (res) => {
        if (res.statusCode >= 200 && res.statusCode < 300) {
          resolve(res.data)
        } else {
          const err = { 
            statusCode: res.statusCode, 
            message: res.data?.message || '请求失败',
            data: res.data
          }
          uni.showToast({ title: err.message, icon: 'none' })
          reject(err)
        }
      },
      fail: (err) => {
        uni.showToast({ title: '网络连接失败', icon: 'none' })
        reject({ 
          statusCode: -1, 
          message: '网络错误', 
          error: err 
        })
      }
    })
  }).catch(err => { 
    if (err.statusCode === 401) {
      uni.navigateTo({ url: '/pages/login/login' })
    }
    throw err // 继续抛出错误
  })
}