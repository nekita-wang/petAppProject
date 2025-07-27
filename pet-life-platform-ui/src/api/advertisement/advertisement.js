import request from "@/utils/request";

// 查询广告信息列表
export function listAdvertisement(query) {
  return request({
    url: "/advertisement/advertisement/list",
    method: "get",
    params: query,
  });
}

// 查询广告信息详细
export function getAdvertisement(id) {
  return request({
    url: "/advertisement/advertisement/" + id,
    method: "get",
  });
}

// 新增广告信息
export function addAdvertisement(data) {
  return request({
    url: "/advertisement/advertisement",
    method: "post",
    data: data,
  });
}

// 修改广告信息
export function updateAdvertisement(data) {
  return request({
    url: "/advertisement/advertisement",
    method: "put",
    data: data,
  });
}

// 删除广告信息
export function delAdvertisement(id) {
  return request({
    url: "/advertisement/advertisement/" + id,
    method: "delete",
  });
}

// 导出广告信息
export function exportAdvertisement(query) {
  return request({
    url: "/advertisement/advertisement/export",
    method: "post",
    params: query,
  });
}

// 获取广告位下拉选项
export function getAdPositionOptions() {
  return request({
    url: "/advertisement/advertisement/adPositionOptions",
    method: "get",
  });
}

// 根据广告位查询正在运行的广告
export function getRunningAdvertisement(adPosition) {
  return request({
    url: "/advertisement/advertisement/running/" + adPosition,
    method: "get",
  });
}

// 失效广告
export function invalidateAdvertisement(id) {
  return request({
    url: "/advertisement/advertisement/invalidate/" + id,
    method: "put",
  });
}

// 结清广告
export function clearanceAdvertisement(data) {
  return request({
    url: "/advertisement/advertisement/clearance",
    method: "put",
    data: data,
  });
}

// 获取广告统计数据
export function getAdvertisementStatistics() {
  return request({
    url: "/advertisement/advertisement/statistics",
    method: "get",
  });
}
