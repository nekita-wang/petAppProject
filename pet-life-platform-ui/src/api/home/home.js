import request from '@/utils/request';

/**
 * 获取首页统计数据
 * @returns {Promise}
 */
export function getHomeStatistics() {
  return request({
    url: '/home/petNum',
    method: 'get'
  });
}

/**
 * 获取宠物分类列表
 * @param {Object} query 查询参数
 * @returns {Promise}
 */
export function select(query) {
  return request({
    url: '/home/select',
    method: 'get',
    params: query
  });
}