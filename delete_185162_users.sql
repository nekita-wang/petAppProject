-- 删除手机号为185162系列的用户
-- 注意：执行前请先备份数据库
-- 表结构：user表，手机号字段为phone，支持+86前缀

-- 1. 首先查看要删除的用户（建议先执行这个查询确认要删除的用户）
SELECT 
    user_id,
    phone,
    nick_name,
    register_time,
    status,
    last_login_time,
    auth,
    minor
FROM user 
WHERE phone LIKE '185162%' OR phone LIKE '+86185162%'
ORDER BY user_id;

-- 2. 统计要删除的用户数量
SELECT 
    COUNT(*) as users_to_delete,
    COUNT(CASE WHEN status = 0 THEN 1 END) as active_users,
    COUNT(CASE WHEN status = 1 THEN 1 END) as deleted_users,
    COUNT(CASE WHEN status = 2 THEN 1 END) as frozen_users
FROM user 
WHERE phone LIKE '185162%' OR phone LIKE '+86185162%';

-- 3. 查看用户状态分布
SELECT 
    status,
    COUNT(*) as count,
    CASE status 
        WHEN 0 THEN '生效'
        WHEN 1 THEN '已注销'
        WHEN 2 THEN '冻结'
        ELSE '未知'
    END as status_name
FROM user 
WHERE phone LIKE '185162%' OR phone LIKE '+86185162%'
GROUP BY status;

-- 4. 删除手机号为185162系列的用户（物理删除）
-- 注意：这里使用物理删除，如果需要逻辑删除，请注释掉这行，使用下面的UPDATE语句
DELETE FROM user 
WHERE phone LIKE '185162%' OR phone LIKE '+86185162%';

-- 4. 逻辑删除：将手机号为185162系列的用户状态改为已注销(1)
-- 如果使用逻辑删除，请注释掉上面的DELETE语句，使用下面的UPDATE语句
-- UPDATE user 
-- SET status = 1 
-- WHERE phone LIKE '185162%' OR phone LIKE '+86185162%';

-- 5. 验证删除结果
SELECT COUNT(*) as remaining_users 
FROM user 
WHERE phone LIKE '185162%' OR phone LIKE '+86185162%';

-- 6. 查看删除后的用户总数
SELECT COUNT(*) as total_users FROM user;

-- 7. 查看删除后的用户列表（前10条，按注册时间倒序）
SELECT 
    user_id,
    phone,
    nick_name,
    register_time,
    status,
    CASE status 
        WHEN 0 THEN '生效'
        WHEN 1 THEN '已注销'
        WHEN 2 THEN '冻结'
        ELSE '未知'
    END as status_name
FROM user 
ORDER BY register_time DESC 
LIMIT 10;

-- 8. 检查是否有其他185162相关的手机号（带其他前缀）
SELECT 
    phone,
    COUNT(*) as count
FROM user 
WHERE phone LIKE '%185162%'
GROUP BY phone
ORDER BY phone; 