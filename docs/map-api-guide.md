# 地图 API 使用指南

## 概述

本项目支持多种地图服务提供商，包括高德地图和天地图。由于高德地图商业使用需要支付 5 万元授权费，推荐使用**天地图**作为免费替代方案。

## 天地图 vs 高德地图对比

| 特性           | 天地图             | 高德地图                |
| -------------- | ------------------ | ----------------------- |
| **费用**       | 完全免费           | 商业使用需 5 万元授权费 |
| **申请难度**   | 简单，个人即可申请 | 企业认证，流程复杂      |
| **功能完整性** | 支持基础 LBS 服务  | 功能更丰富              |
| **数据准确性** | 国家官方数据       | 商业数据，更新频繁      |
| **稳定性**     | 国家平台，稳定可靠 | 商业服务，稳定性好      |

## 天地图 API 申请步骤

### 1. 注册账号

访问 [天地图官网](http://lbs.tianditu.gov.cn/) 注册开发者账号

### 2. 申请成为开发者

- 登录后进入控制台
- 选择"申请成为天地图开发者"
- 填写个人或企业信息

### 3. 创建应用获取 API Key

- 在应用管理中创建新应用
- 选择应用类型（Web 端服务 API）
- 获取 API Key

### 4. 配置项目

在 `application.yml` 中配置：

```yaml
map:
  provider: tianditu # 使用天地图
  tianditu:
    api-key: YOUR_TIANDITU_API_KEY_HERE # 替换为您的API Key
```

## 项目配置

### 配置文件结构

```yaml
map:
  # 地图服务提供商：amap（高德）、tianditu（天地图）
  provider: tianditu
  # 请求超时时间（毫秒）
  timeout: 5000
  # 高德地图配置
  amap:
    api-key: YOUR_AMAP_API_KEY
    geocoding-url: https://restapi.amap.com/v3/geocode/geo
    regeo-url: https://restapi.amap.com/v3/geocode/regeo
  # 天地图配置
  tianditu:
    api-key: YOUR_TIANDITU_API_KEY
    geocoding-url: http://api.tianditu.gov.cn/geocoder
    regeo-url: http://api.tianditu.gov.cn/geocoder
```

### 切换地图服务提供商

只需修改 `provider` 配置即可：

- `tianditu`: 使用天地图（推荐）
- `amap`: 使用高德地图

## API 接口

### 1. 统一地图服务接口

#### 地理编码（地址转坐标）

```
GET /app/map/test/geocoding?address=北京市朝阳区
```

#### 逆地理编码（坐标转地址）

```
GET /app/map/test/reverse-geocoding?longitude=116.40739990&latitude=39.90419989
```

### 2. 天地图专用接口

#### 天地图地理编码

```
GET /app/map/test/tianditu/geocoding?address=北京市朝阳区
```

#### 天地图逆地理编码

```
GET /app/map/test/tianditu/reverse-geocoding?longitude=116.40739990&latitude=39.90419989
```

### 3. 服务状态检查

```
GET /app/map/test/status
```

### 4. 批量地理编码

```
POST /app/map/test/batch-geocoding
Content-Type: application/json

["北京市朝阳区", "上海市浦东新区", "广州市天河区"]
```

## 测试方法

### 1. 使用测试脚本

```bash
chmod +x scripts/test-tianditu-api.sh
./scripts/test-tianditu-api.sh YOUR_TIANDITU_API_KEY
```

### 2. 使用 Swagger UI

启动项目后访问：`http://localhost:8080/swagger-ui.html`

### 3. 使用 curl 命令

```bash
# 测试地理编码
curl "http://localhost:8080/app/map/test/geocoding?address=北京市朝阳区"

# 测试逆地理编码
curl "http://localhost:8080/app/map/test/reverse-geocoding?longitude=116.40739990&latitude=39.90419989"
```

## 代码集成

### 在业务代码中使用

```java
@Autowired
private MapService mapService;

// 地理编码
Map<String, Object> location = mapService.geocoding("北京市朝阳区");

// 逆地理编码
Map<String, Object> address = mapService.reverseGeocoding(
    new BigDecimal("116.40739990"),
    new BigDecimal("39.90419989")
);

// 检查服务是否可用
boolean available = mapService.isServiceAvailable();

// 获取当前使用的服务提供商
String provider = mapService.getCurrentProvider();
```

## 常见问题

### Q: 天地图 API 调用失败怎么办？

A:

1. 检查 API Key 是否正确配置
2. 确认网络连接正常
3. 查看日志中的详细错误信息
4. 验证 API Key 是否有效且未过期

### Q: 如何在高德地图和天地图之间切换？

A: 只需修改配置文件中的 `map.provider` 值即可，无需修改业务代码。

### Q: 天地图的数据准确性如何？

A: 天地图使用国家测绘地理信息局的官方数据，准确性有保障，适合大多数应用场景。

### Q: 是否支持其他地图服务？

A: 当前支持高德地图和天地图，如需支持其他服务（如百度地图、腾讯地图），可以扩展 MapService。

## 业务功能集成

### "附近"功能模块集成

天地图 API 已完全集成到"附近"功能模块中，实现以下业务需求：

#### 1. 定位器功能 (2.1.1)

- **地址搜索定位**: `GET /app/nearby/location/search?address=北京市朝阳区`
- **确认地图定位**: `POST /app/nearby/location/confirm`
- **定位精度**: ≤10 米（符合需求）
- **定位方式**: GPS + 基站 + Wi-Fi 三角定位

#### 2. 筛选器功能 (2.1.2)

- **条件筛选**: `POST /app/nearby/users/filter`
- **支持筛选条件**:
  - 宠物品种：模糊匹配（如"布偶"匹配"布偶猫"）
  - 距离：0-1km, 1-3km, 3-5km, 5km 以上
  - 在线时间：基于最后登录时间计算
  - 年龄范围：可调节年龄段
  - 性别：多选支持

#### 3. 搜索框功能 (2.1.3)

- **关键词搜索**: `GET /app/nearby/search?keyword=哈士奇`
- **支持搜索**: 宠物品种、用户名模糊匹配

#### 4. 正文展示功能 (2.1.6)

- **附近用户列表**: `GET /app/nearby/users`
- **卡片信息包含**:
  - 头像、昵称、距离（实时计算）
  - 在线时间（精确到分钟/小时/天）
  - 个性签名（最多 2 行，超出省略）
  - 宠物品种+性别（最多显示 2 个，超出显示+数字）

#### 5. 实时位置更新

- **位置更新**: `POST /app/nearby/location/update`
- **更新频率**: 每 60 秒获取一次坐标
- **距离计算**: 使用 Haversine 公式精确计算
- **移动阈值**: 移动距离>10 米才更新位置

### 技术实现特点

1. **精确距离计算**: 使用 Haversine 公式，精度达到米级
2. **智能位置更新**: 只有移动距离超过阈值才更新，节省资源
3. **多条件筛选**: 支持距离、品种、在线时间等组合筛选
4. **格式化显示**: 距离自动格式化（如 500m、1.2km）
5. **容错处理**: API 调用失败时使用本地模拟数据

## 推荐方案

对于您的宠物生活平台项目，推荐使用**天地图**：

1. **成本优势**：完全免费，无需支付高额授权费
2. **功能满足**：支持地理编码、逆地理编码等基础 LBS 功能
3. **合规性**：国家官方平台，数据合规
4. **稳定性**：政府背景，服务稳定可靠
5. **业务集成**：已完全集成到"附近"功能模块

如果后续业务发展需要更高级的功能，可以随时切换到其他地图服务提供商。
