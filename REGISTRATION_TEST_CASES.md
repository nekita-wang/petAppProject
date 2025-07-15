# 注册流程测试用例

## 测试场景 1：验证码通过后，未填写个人资料

### 步骤

1. 发送验证码

```bash
POST /app/auth/sendCode
{
  "phone": "13812345678"
}
```

2. 校验验证码

```bash
POST /app/auth/verifyCode
{
  "phone": "13812345678",
  "code": "123456"
}
```

3. 验证码校验通过，返回临时 token
4. **用户直接退出，不调用 stepRegister 接口**

### 预期结果

- 数据库中无用户记录
- 验证码已从 Redis 中删除
- 临时 token 在 10 分钟后过期

## 测试场景 2：验证码通过后，填写个人资料但跳过宠物信息

### 步骤

1. 发送验证码

```bash
POST /app/auth/sendCode
{
  "phone": "13812345678"
}
```

2. 校验验证码

```bash
POST /app/auth/verifyCode
{
  "phone": "13812345678",
  "code": "123456"
}
```

3. 分步注册（跳过宠物信息）

```bash
POST /app/auth/stepRegister
{
  "phone": "13812345678",
  "nickName": "测试用户",
  "password": "encryptedPassword",
  "birthday": "2000-01-01",
  "gender": 1,
  "minor": 0,
  "avatarUrl": "/profile/avatar/default.jpg",
  "pets": null
}
```

### 预期结果

- 用户表中新增一条记录
- 宠物信息表中无记录
- 返回登录 token
- needPetInfo 字段为 true（表示需要填写宠物信息）

## 测试场景 3：验证码通过后，填写个人资料和宠物信息

### 步骤

1. 发送验证码

```bash
POST /app/auth/sendCode
{
  "phone": "13812345678"
}
```

2. 校验验证码

```bash
POST /app/auth/verifyCode
{
  "phone": "13812345678",
  "code": "123456"
}
```

3. 分步注册（包含宠物信息）

```bash
POST /app/auth/stepRegister
{
  "phone": "13812345678",
  "nickName": "测试用户",
  "password": "encryptedPassword",
  "birthday": "2000-01-01",
  "gender": 1,
  "minor": 0,
  "avatarUrl": "/profile/avatar/default.jpg",
  "pets": [
    {
      "petAvatarURL": "/profile/pet/avatar.jpg",
      "petBreed": "金毛",
      "petNickName": "小金",
      "petClass": "狗",
      "petGender": 1,
      "sterilized": 0,
      "petBirthday": "2020-01-01",
      "adoptionDate": "2020-03-01"
    }
  ]
}
```

### 预期结果

- 用户表中新增一条记录
- 宠物信息表中新增一条记录
- 返回登录 token
- needPetInfo 字段为 false（表示不需要填写宠物信息）

## 测试场景 4：验证码错误

### 步骤

1. 发送验证码
2. 使用错误验证码校验

```bash
POST /app/auth/verifyCode
{
  "phone": "13812345678",
  "code": "000000"
}
```

### 预期结果

- 返回验证码错误
- 数据库中无用户记录

## 测试场景 5：手机号已注册

### 步骤

1. 使用已注册的手机号发送验证码
2. 校验验证码

### 预期结果

- 返回手机号已注册错误
- 数据库中无新用户记录

## 测试场景 6：昵称已存在

### 步骤

1. 发送验证码
2. 校验验证码
3. 使用已存在的昵称进行注册

### 预期结果

- 返回昵称已存在错误
- 数据库中无新用户记录

## 测试场景 7：密码强度不足

### 步骤

1. 发送验证码
2. 校验验证码
3. 使用弱密码进行注册

### 预期结果

- 返回密码强度不足错误
- 数据库中无新用户记录

## 注意事项

1. **验证码有效期**：验证码在 Redis 中存储 5 分钟
2. **临时 token 有效期**：临时 token 在 Redis 中存储 10 分钟
3. **数据一致性**：用户和宠物信息在同一个事务中处理
4. **错误处理**：所有错误都会返回相应的错误码和消息
5. **日志记录**：所有关键操作都会记录日志
