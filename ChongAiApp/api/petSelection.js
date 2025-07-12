import {
	request
} from '@/utils/request.js'

// 宠物分类tab
export function apiGetPetTypeList() {
	return request({
		url: "/app/pet/pet",
		method: 'GET'
	})
}

// 获取宠物品种列表
export function apiGetPetBreeds(petClass, petBreed) {
	return request({
		url: '/app/pet/breeds',
		method: 'GET',
		data: {
			petClass,
			petBreed
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