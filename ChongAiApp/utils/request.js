const BASE_URL = 'https://637c-112-48-4-41.ngrok-free.app';
// const BASE_URL = 'http://localhost:8080';

// 请求拦截器
const requestInterceptor = (config) => {
  // 从本地存储获取token
  const token = uni.getStorageSync('token');
  
  // 如果存在token，添加到请求头
  if (token) {
    config.header = {
      ...config.header,
      'Authorization': `Bearer ${token}`
    };
  }

  
  return config;
}

export function request(config = {}) {
  let {
    url,
    method = "GET",
    data = {},
    header = {}
  } = config;

  url = BASE_URL + url;

  // 统一处理请求头
  header = {
    'Content-Type': 'application/json',
    ...header  // 允许外部覆盖
  };

  // 应用请求拦截器
  const interceptedConfig = requestInterceptor({
    url,
    method,
    data,
    header
  });

  return new Promise((resolve, reject) => {
    uni.request({
      url: interceptedConfig.url,
      method: interceptedConfig.method,
      data: JSON.stringify(interceptedConfig.data),
      header: interceptedConfig.header,
      success: (res) => {
        // 可以在这里添加响应拦截器逻辑
        if (res.statusCode >= 200 && res.statusCode < 300) {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      fail: (err) => {
        reject({
          errMsg: err.errMsg,
          statusCode: -1
        })
      }
    })
  })
}