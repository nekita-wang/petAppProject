import request from '@/utils/request'

// 查询宠物分类列表
export function listPetType(query) {
  return request({
    url: '/petType/list',
    method: 'get',
    params: query
  })
}

// 查询宠物类别
export function listPet() {
  return request({
    url: '/petType/pet',
    method: 'get'
  })
}


// 查询宠物分类详细
export function getPetType(petClassId) {
  return request({
    url: '/petType/' + petClassId,
    method: 'get'
  })
}

// 新增宠物分类
export function addPetType(data) {
  return request({
    url: '/petType',
    method: 'post',
    data: data
  })
}

// 修改宠物分类
export function updatePetType(data) {
  return request({
    url: '/petType',
    method: 'put',
    data: data
  })
}

// 删除宠物分类
export function delPetType(petClassId) {
  return request({
    url: '/petType/' + petClassId,
    method: 'delete'
  })
}
// 批量更新宠物分类状态
export function updatePetTypeStatusBatch(data) {
  return request({
    url: '/petType/updateStatusBatch',
    method: 'put',
    data: data
  });
}