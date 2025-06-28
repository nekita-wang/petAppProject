import request from '@/utils/request'

// 查询宠物品种列表
export function listBreed(query) {
  return request({
    url: '/breed/list',
    method: 'get',
    params: query
  })
}

// 查询宠物品种详细
export function getBreed(petClassId) {
  return request({
    url: '/breed/' + petClassId,
    method: 'get'
  })
}

// 新增宠物品种
export function addBreed(data) {
  return request({
    url: '/breed',
    method: 'post',
    data: data
  })
}

// 修改宠物品种
export function updateBreed(data) {
  return request({
    url: '/breed',
    method: 'put',
    data: data
  })
}

// 删除宠物品种
export function delBreed(petClassId) {
  return request({
    url: '/breed/' + petClassId,
    method: 'delete'
  })
}

// 批量更新宠物分类状态
export function updateBreedStatus(data) {
  return request({
    url: '/breed/updateBreedStatus',
    method: 'put',
    data: data
  });
}
