# 短信服务测试指南

## 快速测试步骤

### 1. 启动应用
```bash
cd pet-life-platform-admin
mvn spring-boot:run
```

### 2. 查看启动日志
应用启动时会显示短信服务配置验证信息：
```
开始验证短信服务配置...
基本配置验证通过: provider=wangwei, timeout=10000ms
服务提供商配置验证通过: wangwei
短信服务可用性验证通过: 当前提供商=wangwei, 可用提供商=wangwei, sanwang
短信服务配置验证完成 ✓
```

### 3. 测试验证码发送接口

#### 使用curl测试：
```bash
# 发送验证码
curl -X POST "http://localhost:8080/auth/sendLoginCode" \
  -H "Content-Type: application/json" \
  -d '{"phone": "13800138000"}'
```

#### 预期响应：
```json
{
  "code": 200,
  "msg": "验证码发送成功",
  "data": "验证码发送成功"
}
```

### 4. 测试短信服务管理接口

#### 查看当前状态：
```bash
curl -X GET "http://localhost:8080/system/sms/status" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

#### 切换服务商：
```bash
curl -X PUT "http://localhost:8080/system/sms/provider/sanwang" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

#### 测试短信发送：
```bash
curl -X POST "http://localhost:8080/system/sms/test?phone=13800138000" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 日志监控

### 成功发送日志：
```
发送短信请求: URL=https://wwsms.market.alicloudapi.com/send_sms, Headers={...}, Body={...}
望为短信发送响应: {"return_code":"00000","message":"发送成功"}
验证码发送成功: phone=13800138000, provider=wangwei
```

### 失败发送日志：
```
望为短信发送响应: {"return_code":"40001","message":"余额不足"}
验证码发送失败: phone=13800138000, provider=wangwei, error=余额不足
```

## 常见问题排查

### 1. 配置问题
- 检查 `application.yml` 中的 AppCode、AppKey 等配置
- 确认短信服务商账户状态和余额

### 2. 网络问题
- 检查服务器网络连接
- 确认防火墙设置

### 3. 模板问题
- 确认短信模板已在服务商后台配置
- 检查模板ID是否正确

### 4. 依赖问题
- 确保按正确顺序编译了所有模块
- 检查 Maven 依赖是否正确下载

## 生产环境注意事项

1. **替换真实配置**：将配置文件中的 AppCode、AppKey、AppSecret 替换为真实值
2. **设置发送频率限制**：避免恶意调用导致费用过高
3. **监控发送成功率**：及时发现问题并切换服务商
4. **备份服务商**：配置多个服务商以确保服务可用性
