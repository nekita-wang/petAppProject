-- 紧急修复：创建缺失的 search_history 表
-- 执行此脚本解决地址搜索功能报错问题

USE `pet-life-platform`;

-- 创建搜索历史表
CREATE TABLE IF NOT EXISTS `search_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyword` varchar(255) NOT NULL COMMENT '搜索关键词',
  `result_address` varchar(500) DEFAULT NULL COMMENT '搜索结果地址',
  `successful` tinyint(1) NOT NULL DEFAULT '0' COMMENT '搜索是否成功 (0=失败, 1=成功)',
  `search_count` int NOT NULL DEFAULT '1' COMMENT '搜索次数',
  `last_search_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后搜索时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_keyword` (`keyword`) COMMENT '关键词唯一索引',
  KEY `idx_successful` (`successful`) COMMENT '成功状态索引',
  KEY `idx_search_count` (`search_count`) COMMENT '搜索次数索引',
  KEY `idx_last_search_time` (`last_search_time`) COMMENT '最后搜索时间索引',
  KEY `idx_create_time` (`create_time`) COMMENT '创建时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索历史记录表';

-- 插入一些测试数据
INSERT IGNORE INTO `search_history` (`keyword`, `result_address`, `successful`, `search_count`, `last_search_time`, `create_time`, `update_time`) VALUES
('厦门大学', '福建省厦门市思明区思明南路422号厦门大学', 1, 1, NOW(), NOW(), NOW()),
('北京大学', '北京市海淀区颐和园路5号北京大学', 1, 1, NOW(), NOW(), NOW()),
('清华大学', '北京市海淀区清华园1号清华大学', 1, 1, NOW(), NOW(), NOW()),
('天安门广场', '北京市东城区天安门广场', 1, 1, NOW(), NOW(), NOW()),
('上海外滩', '上海市黄浦区中山东一路外滩', 1, 1, NOW(), NOW(), NOW());

-- 验证表是否创建成功
SELECT 'search_history表创建成功' as status, COUNT(*) as record_count FROM search_history;
