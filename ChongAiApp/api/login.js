import { request } from '@/utils/request.js'

// 手机密码登录接口(判断是否新用户)
export function apiGetPwd(grantType,phone, password,code) {
  return request({
    url: "/app/auth/login",
    method: 'POST',
    data: {
      grantType, 
      phone,
      password,
	  code
    }
  })
}
// 获取手机验证码接口
export function apiGetCode(phone) {
  return request({
    url: "/app/auth/sendCode",
    method: 'POST',
    data: {
      phone: phone
    }
  })
}
// 完善个人资料接口
export function apiCompleteProfile(data) {
  return request({
    url: "/app/user/completeProfile",
    method: 'POST',
    data
  })
}