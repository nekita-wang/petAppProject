import {
	request
} from '@/utils/request.js'

// 宠物品种列表
export function apiGetBreedList(petClass,petBreed) {
  return request({
    url: '/app/pet/breeds',
    method: 'GET',
    data: {
      petClass,  
      petBreed   
    }
  })
}
//宠物分类tab
export function apiGetPetTypeList() {
	return request({
		url: "/app/pet/pet",
		method: 'GET',
		header: {
			'ngrok-skip-browser-warning': 'true' //测试 添加请求头绕过ngrok拦截
		}
	})
}
// 添加宠物
export function apiaddPet(data) {
	return request({
		url: "/app/pet/addPet",
		method: 'POST',
		data
	})
}