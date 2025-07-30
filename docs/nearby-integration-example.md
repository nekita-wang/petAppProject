# "附近"功能与天地图 API 集成示例

## 业务场景对应的 API 调用

### 1. 定位器功能 (2.1.1)

#### 场景：用户在搜索框输入地址，获取精确坐标

```bash
# 请求示例
curl -X GET "http://localhost:8080/app/nearby/location/search?address=北京市朝阳区三里屯"

# 响应示例
{
  "code": 200,
  "msg": "定位成功",
  "data": {
    "searchAddress": "北京市朝阳区三里屯",
    "longitude": "116.45678",
    "latitude": "39.93456",
    "accuracy": "≤10米",
    "locationType": "GPS + 基站 + Wi-Fi 三角定位",
    "provider": "tianditu",
    "timestamp": 1640995200000
  }
}
```

#### 场景：用户确认地图定位，更新位置

```bash
# 请求示例
curl -X POST "http://localhost:8080/app/nearby/location/confirm" \
  -d "userId=1&longitude=116.45678&latitude=39.93456&address=北京市朝阳区三里屯"

# 响应示例
{
  "code": 200,
  "msg": "地图定位确认成功",
  "data": {
    "userId": 1,
    "longitude": "116.45678",
    "latitude": "39.93456",
    "address": "北京市朝阳区三里屯",
    "updateTime": 1640995200000,
    "message": "位置更新成功，附近用户距离已刷新"
  }
}
```

### 2. 筛选器功能 (2.1.2)

#### 场景：筛选距离 0-1km 内的哈士奇主人

```bash
# 请求示例
curl -X POST "http://localhost:8080/app/nearby/users/filter?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "distanceType": 1,
    "petBreedKeyword": "哈士奇",
    "pageNum": 1,
    "pageSize": 10
  }'

# 响应示例
{
  "code": 200,
  "msg": "查询成功",
  "rows": [
    {
      "userId": 2,
      "avatarUrl": "https://example.com/avatar2.jpg",
      "nickName": "狗狗爱好者",
      "distance": 500.5,
      "distanceText": "500m",
      "onlineDuration": "3分前",
      "onlineColor": "black",
      "personalSignature": "我家有只可爱的哈士奇，喜欢交朋友",
      "petList": [
        {
          "petBreed": "哈士奇",
          "petGender": 1,
          "petGenderText": "♂",
          "displayText": "哈士奇♂"
        }
      ],
      "displayPetCount": 1,
      "remainingPetCount": 0
    }
  ],
  "total": 1
}
```

### 3. 搜索框功能 (2.1.3)

#### 场景：搜索关键词"布偶"

```bash
# 请求示例
curl -X GET "http://localhost:8080/app/nearby/search?userId=1&keyword=布偶&pageNum=1&pageSize=10"

# 响应示例
{
  "code": 200,
  "msg": "查询成功",
  "rows": [
    {
      "userId": 3,
      "nickName": "布偶猫妈妈",
      "petList": [
        {
          "petBreed": "布偶猫",
          "petGender": 2,
          "displayText": "布偶猫♀"
        }
      ]
    }
  ],
  "total": 1
}
```

### 4. 正文展示功能 (2.1.6)

#### 场景：获取附近用户列表，卡片式展示

```bash
# 请求示例
curl -X GET "http://localhost:8080/app/nearby/users?userId=1&pageNum=1&pageSize=10&radius=5000"

# 响应示例
{
  "code": 200,
  "msg": "查询成功",
  "rows": [
    {
      "userId": 2,
      "avatarUrl": "https://example.com/avatar2.jpg",
      "nickName": "宠物达人",
      "distance": 1200.8,
      "distanceText": "1.2km",
      "onlineDuration": "17小时前",
      "onlineColor": "black",
      "personalSignature": "热爱生活，热爱宠物，希望能和志同道合的朋友一起分享养宠心得...",
      "petList": [
        {
          "petBreed": "金毛",
          "petGender": 1,
          "displayText": "金毛♂"
        },
        {
          "petBreed": "布偶猫",
          "petGender": 2,
          "displayText": "布偶猫♀"
        }
      ],
      "displayPetCount": 2,
      "remainingPetCount": 1
    }
  ],
  "total": 15
}
```

### 5. 实时位置更新

#### 场景：每 60 秒更新一次用户位置

```bash
# 请求示例
curl -X POST "http://localhost:8080/app/nearby/location/update?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": 39.90419989,
    "longitude": 116.40739990,
    "accuracy": 8.5,
    "locationType": 4,
    "address": "天安门广场",
    "deviceType": "iOS",
    "appVersion": "1.0.0"
  }'

# 响应示例
{
  "code": 200,
  "msg": "位置更新成功",
  "data": {
    "userId": 1,
    "movedDistance": 15.6,
    "updateRequired": true,
    "message": "位置已更新，移动距离15.6米"
  }
}
```

## 核心业务逻辑

### 距离计算逻辑

```java
// 使用Haversine公式计算精确距离
BigDecimal distance = locationService.calculateDistance(
    userLat, userLon, targetLat, targetLon
);

// 格式化距离显示
String distanceText = locationService.formatDistance(distance);
// 结果：500m, 1.2km, 15km
```

### 在线时间计算逻辑

```java
// 根据最后登录时间计算在线状态
long timeDiff = System.currentTimeMillis() - lastLoginTime;

if (timeDiff <= 60000) {
    return "在线"; // 绿色
} else if (timeDiff <= 3600000) {
    return (timeDiff / 60000) + "分前"; // 黑色
} else if (timeDiff <= 86400000) {
    return (timeDiff / 3600000) + "小时前"; // 黑色
} else if (timeDiff <= 2592000000L) {
    return (timeDiff / 86400000) + "天前"; // 黑色
} else {
    return "30天前"; // 黑色
}
```

### 宠物信息展示逻辑

```java
// 最多显示2个宠物，超出部分显示+数字
List<PetBriefInfo> displayPets = petList.stream()
    .limit(2)
    .collect(Collectors.toList());

int remainingCount = Math.max(0, petList.size() - 2);

// 格式：哈士奇♂|布偶猫♀ +3
```

## 配置要点

### 1. 天地图 API 配置

```yaml
map:
  provider: tianditu
  timeout: 5000
  tianditu:
    api-key: YOUR_TIANDITU_API_KEY # 从天地图官网申请
    geocoding-url: http://api.tianditu.gov.cn/geocoder
    regeo-url: http://api.tianditu.gov.cn/geocoder
```

### 2. 定位精度要求

- **GPS 定位精度**: ≤10 米
- **更新频率**: 每 60 秒
- **移动阈值**: 移动距离>10 米才更新
- **定位方式**: GPS + 基站 + Wi-Fi 三角定位

### 3. 筛选条件支持

- **距离筛选**: 0-1km, 1-3km, 3-5km, 5km 以上
- **宠物品种**: 模糊匹配（如"布偶"匹配"布偶猫"）
- **在线时间**: 在线、1 小时内、1 天内、1 周内、1 月内
- **年龄范围**: 可调节年龄段
- **性别**: 多选支持

## 错误处理

### 1. 天地图 API 调用失败

```java
// 如果天地图API调用失败，使用本地模拟数据
if (result == null) {
    log.warn("天地图API调用失败，使用本地模拟数据: {}", address);
    result = parseAddressToLocation(address);
}
```

### 2. 坐标验证

```java
// 验证坐标有效性
public boolean isValidCoordinate(BigDecimal latitude, BigDecimal longitude) {
    return latitude != null && longitude != null &&
           latitude.compareTo(new BigDecimal("-90")) >= 0 &&
           latitude.compareTo(new BigDecimal("90")) <= 0 &&
           longitude.compareTo(new BigDecimal("-180")) >= 0 &&
           longitude.compareTo(new BigDecimal("180")) <= 0;
}
```

## 核心业务类说明

### 1. NearbyController

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/controller/NearbyController.java`
- **功能**: "附近"功能的统一入口，集成天地图 API
- **主要接口**:
  - 定位器: `/app/nearby/location/search`, `/app/nearby/location/confirm`
  - 筛选器: `/app/nearby/users/filter`
  - 搜索框: `/app/nearby/search`
  - 正文展示: `/app/nearby/users`

### 2. NearbyUserServiceImpl

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/service/impl/NearbyUserServiceImpl.java`
- **功能**: 附近用户业务逻辑实现
- **核心方法**:
  - `updateUserLocation()`: 更新用户位置，集成天地图 API
  - `getNearbyUsers()`: 获取附近用户列表
  - `getNearbyUsersWithFilter()`: 带筛选条件的查询
  - `calculateOnlineStatus()`: 计算在线状态显示

### 3. LocationService

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/service/LocationService.java`
- **功能**: 位置相关业务逻辑
- **核心方法**:
  - `calculateDistance()`: 使用 Haversine 公式计算精确距离
  - `formatDistance()`: 格式化距离显示
  - `isValidCoordinate()`: 坐标有效性验证
  - `getCoordinatesByAddress()`: 地址转坐标（集成天地图）

### 4. MapService

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/service/MapService.java`
- **功能**: 统一地图服务，支持高德地图和天地图切换
- **核心方法**:
  - `geocoding()`: 地理编码
  - `reverseGeocoding()`: 逆地理编码
  - `getCurrentProvider()`: 获取当前地图服务提供商

### 5. TiandituService

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/service/TiandituService.java`
- **功能**: 天地图 API 专用服务
- **特点**: 完全免费，无需商业授权费

### 6. TextDisplayUtil

- **路径**: `pet-life-platform-app/src/main/java/com/petlife/platform/app/util/TextDisplayUtil.java`
- **功能**: 文本显示工具类
- **核心方法**:
  - `truncatePersonalSignature()`: 个性签名截断（最多 2 行）
  - `formatPetBreedsDisplay()`: 宠物品种显示（最多 2 个+数字）
  - `formatOnlineStatus()`: 在线状态格式化

## 业务流程总结

### 用户定位流程

1. 用户输入地址 → 调用天地图 API 地理编码 → 返回精确坐标
2. 用户确认位置 → 更新数据库位置信息 → 刷新附近用户距离

### 附近用户查询流程

1. 获取用户当前位置 → 使用 Haversine 公式计算距离 → 应用筛选条件
2. 格式化显示数据 → 截断个性签名 → 处理宠物信息显示

### 实时更新流程

1. 每 60 秒获取 GPS 坐标 → 计算移动距离 → 超过 10 米阈值才更新
2. 更新在线状态 → 重新计算附近用户距离

这样的集成方案既满足了您的业务需求，又充分利用了天地图 API 的免费优势，避免了高德地图的高额授权费用。
