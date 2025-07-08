import { request } from '@/utils/request.js'

// 宠物分类列表
export function apiGetBreedList(petClass,pageNum, pageSize) {
  return request({
    url: "/app/pet/breeds",
    method: 'GET',
	header: {
		'ngrok-skip-browser-warning': 'true' //测试 添加请求头绕过ngrok拦截
	},
    data: {
      petClass, 
      pageNum: 1,
      pageSize: 9999,
    }
  })
}
//宠物列表
export function apiGetPetTypeList() {
  return request({
    url: "/app/pet/pet",
    method: 'GET',
	header: {
		'ngrok-skip-browser-warning': 'true' //测试 添加请求头绕过ngrok拦截
	}
  })
}