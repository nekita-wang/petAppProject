# 短信服务集成说明

## 概述

本项目已集成了真正的短信验证码发送服务，支持多个短信服务商，并可以动态切换。目前支持：

1. **望为短信服务** (wangwei)
2. **三网 106 短信服务** (sanwang)

## 配置说明

### 1. 配置文件 (application.yml)

```yaml
# 短信服务配置
sms:
  # 当前使用的服务商：wangwei（望为）、sanwang（三网106）
  provider: wangwei
  # 请求超时时间（毫秒）
  timeout: 10000
  # 验证码模板配置
  templates:
    # 登录验证码模板
    login-code:
      content: "code:{code}"
      expire-minutes: 5
  # 望为短信配置
  wangwei:
    host: https://wwsms.market.alicloudapi.com
    path: /send_sms
    app-code: fd4e6d2d32214e16a4b5e2bfe0857d86
    app-key: 204633337
    app-secret: jL82g8Q1N79SzKuMpP8dnLkIe7S0Kf7w
    template-id: wangweisms996
  # 三网106短信配置
  sanwang:
    host: https://gyytz.market.alicloudapi.com
    path: /sms/smsSend
    app-code: fd4e6d2d32214e16a4b5e2bfe0857d86
    app-key: 204633337
    app-secret: jL82g8Q1N79SzKuMpP8dnLkIe7S0Kf7w
    sms-sign-id: 2e65b1bb3d054466b82f0c9d125465e2
    template-id: 908e94ccf08b4476ba6c876d13f084ad
```

### 2. 配置参数说明

#### 望为短信服务参数：

- `host`: API 主机地址
- `path`: API 路径
- `app-code`: AppCode（用于 Authorization 头）
- `app-key`: AppKey
- `app-secret`: AppSecret
- `template-id`: 短信模板 ID

#### 三网 106 短信服务参数：

- `host`: API 主机地址
- `path`: API 路径
- `app-code`: AppCode（用于 Authorization 头）
- `app-key`: AppKey
- `app-secret`: AppSecret
- `sms-sign-id`: 短信签名 ID
- `template-id`: 短信模板 ID

## 使用方法

### 1. 发送验证码

验证码发送已集成到现有的登录流程中，调用 `/auth/sendLoginCode` 接口即可：

```json
POST /auth/sendLoginCode
{
    "phone": "13800138000"
}
```

### 2. 管理短信服务

#### 查看当前状态

```http
GET /system/sms/status
```

响应：

```json
{
  "code": 200,
  "data": {
    "currentProvider": "wangwei",
    "availableProviders": ["wangwei", "sanwang"]
  }
}
```

#### 切换服务商

```http
PUT /system/sms/provider/sanwang
```

#### 测试短信发送

```http
POST /system/sms/test?phone=13800138000
```

## 架构设计

### 1. 核心组件

- **SmsConfig**: 短信服务配置类
- **SmsProvider**: 短信服务提供商接口
- **SmsService**: 短信服务管理器
- **SmsResult**: 短信发送结果封装
- **WangweiSmsProvider**: 望为短信服务实现
- **SanwangSmsProvider**: 三网 106 短信服务实现

### 2. 设计特点

- **可扩展性**: 通过实现 `SmsProvider` 接口可以轻松添加新的短信服务商
- **可配置性**: 支持通过配置文件切换服务商和调整参数
- **容错性**: 完善的异常处理和错误码定义
- **可监控性**: 详细的日志记录和状态查询

## 添加新的短信服务商

### 1. 实现 SmsProvider 接口

```java
@Component("newProviderSmsProvider")
public class NewProviderSmsProvider implements SmsProvider {

    @Override
    public SmsResult sendVerificationCode(String phone, String code) {
        // 实现具体的短信发送逻辑
        return SmsResult.success(getProviderName(), "发送成功");
    }

    @Override
    public String getProviderName() {
        return "newProvider";
    }
}
```

### 2. 添加配置

在 `SmsConfig` 中添加新的配置类：

```java
@Data
public static class NewProviderSettings {
    private String apiUrl;
    private String apiKey;
    // 其他配置参数
}
```

### 3. 更新配置文件

在 `application.yml` 中添加新服务商的配置。

## 注意事项

1. **生产环境配置**: 请确保在生产环境中使用真实的 AppCode、AppKey 和 AppSecret
2. **模板配置**: 不同服务商的模板格式可能不同，请根据实际情况调整
3. **错误处理**: 建议监控短信发送失败率，及时切换服务商
4. **成本控制**: 建议设置短信发送频率限制，避免恶意调用

## 故障排查

### 1. 常见错误

- **配置错误**: 检查 AppCode、AppKey 等配置是否正确
- **网络问题**: 检查服务器网络连接
- **模板问题**: 检查短信模板是否正确配置
- **余额不足**: 检查短信服务商账户余额

### 2. 日志查看

查看应用日志中的短信发送相关信息：

```
发送短信请求: URL=..., Headers=..., Body=...
短信发送响应: {...}
验证码发送成功: phone=..., provider=...
```

## 依赖修改

为了支持短信服务，我们对项目进行了以下依赖修改：

### 1. 主 pom.xml 修改

- 添加了 `httpclient.version` 版本管理
- 在 `dependencyManagement` 中添加了 `org.apache.httpcomponents:httpclient` 依赖

### 2. common 模块 pom.xml 修改

- 添加了 `org.apache.httpcomponents:httpclient` 依赖

### 3. 新增文件列表

- `SmsConfig.java` - 短信服务配置类
- `SmsProvider.java` - 短信服务提供商接口
- `SmsResult.java` - 短信发送结果封装
- `SmsService.java` - 短信服务管理器
- `SmsHttpUtils.java` - HTTP 工具类
- `WangweiSmsProvider.java` - 望为短信服务实现
- `SanwangSmsProvider.java` - 三网 106 短信服务实现
- `SmsAutoConfiguration.java` - 自动配置类
- `SmsConfigValidator.java` - 配置验证器
- `SmsManagementController.java` - 管理控制器

### 4. 修改的文件

- `AuthServiceImpl.java` - 集成真实短信发送
- `AuthExceptionCode.java` - 添加短信发送失败错误码
- `application.yml` - 添加短信服务配置

## 编译和部署

### 1. 编译顺序

由于模块间的依赖关系，请按以下顺序编译：

```bash
# 1. 编译并安装common模块
cd pet-life-platform-common
mvn clean install

# 2. 编译app模块
cd ../pet-life-platform-app
mvn clean compile

# 3. 编译admin模块
cd ../pet-life-platform-admin
mvn clean compile
```

### 2. 启动验证

应用启动时会自动验证短信服务配置，查看日志确认配置正确：

```
开始验证短信服务配置...
基本配置验证通过: provider=wangwei, timeout=10000ms
服务提供商配置验证通过: wangwei
短信服务可用性验证通过: 当前提供商=wangwei, 可用提供商=wangwei, sanwang
短信服务配置验证完成 ✓
```

## 更新记录

- 2025-07-31: 初始版本，支持望为和三网 106 短信服务
- 2025-07-31: 添加依赖管理和配置验证功能
