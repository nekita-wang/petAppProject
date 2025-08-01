#!/bin/bash

# 附近功能测试脚本
BASE_URL="http://localhost:8080"
USER_ID=1

echo "=== 宠爱生活平台 - 附近功能测试 ==="
echo "Base URL: $BASE_URL"
echo "Test User ID: $USER_ID"
echo ""

# 1. 测试定位器 - 地址搜索定位
echo "1. 测试定位器 - 地址搜索定位"
echo "搜索地址: 北京市朝阳区三里屯"
curl -s -X GET "$BASE_URL/app/nearby/location/search?address=北京市朝阳区三里屯" | jq '.'
echo ""

# 2. 测试定位器 - 确认地图定位
echo "2. 测试定位器 - 确认地图定位"
echo "设置用户位置为天安门坐标"
curl -s -X POST "$BASE_URL/app/nearby/location/confirm" \
  -d "userId=$USER_ID&longitude=116.40739990&latitude=39.90419989&address=天安门广场" | jq '.'
echo ""

# 3. 测试正文展示 - 获取附近用户列表
echo "3. 测试正文展示 - 获取附近用户列表"
curl -s -X GET "$BASE_URL/app/nearby/users?userId=$USER_ID&pageNum=1&pageSize=10&radius=5000" | jq '.'
echo ""

# 4. 测试筛选器 - 条件筛选
echo "4. 测试筛选器 - 条件筛选（距离0-1km，宠物品种：哈士奇）"
curl -s -X POST "$BASE_URL/app/nearby/users/filter?userId=$USER_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "distanceType": 1,
    "petBreed": "哈士奇",
    "pageNum": 1,
    "pageSize": 10
  }' | jq '.'
echo ""

# 5. 测试搜索框 - 关键词搜索
echo "5. 测试搜索框 - 关键词搜索"
echo "搜索关键词: 布偶"
curl -s -X GET "$BASE_URL/app/nearby/search?userId=$USER_ID&keyword=布偶&pageNum=1&pageSize=10" | jq '.'
echo ""

# 6. 测试距离计算
echo "6. 测试距离计算"
echo "计算天安门到故宫的距离"
curl -s -X GET "$BASE_URL/app/nearby/distance/calculate?lat1=39.90419989&lon1=116.40739990&lat2=39.91649&lon2=116.39694" | jq '.'
echo ""

# 7. 测试逆地理编码
echo "7. 测试逆地理编码"
echo "将天安门坐标转换为地址"
curl -s -X GET "$BASE_URL/app/nearby/location/reverse?longitude=116.40739990&latitude=39.90419989" | jq '.'
echo ""

# 8. 测试实时位置更新
echo "8. 测试实时位置更新"
echo "更新用户位置到故宫"
curl -s -X POST "$BASE_URL/app/nearby/location/update?userId=$USER_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": 39.91649,
    "longitude": 116.39694,
    "accuracy": 10.0,
    "locationType": 4,
    "address": "故宫博物院"
  }' | jq '.'
echo ""

echo "=== 测试完成 ==="
echo ""
echo "💡 提示："
echo "1. 确保应用已启动在 $BASE_URL"
echo "2. 确保数据库中有测试用户数据"
echo "3. 确保天地图API Key已正确配置"
echo "4. 如果某些接口返回错误，请检查数据库表结构和数据"
