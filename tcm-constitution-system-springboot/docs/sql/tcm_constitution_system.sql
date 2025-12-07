/*
 Navicat Premium Data Transfer

 Source Server         : Zhuyanmei_localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : tcm_constitution_system

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/12/2025 18:36:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assessment
-- ----------------------------
DROP TABLE IF EXISTS `assessment`;
CREATE TABLE `assessment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测评ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `scores` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '九种体质得分',
  `main_constitution` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主要体质类型',
  `secondary_constitutions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '兼夹体质（JSON数组）',
  `advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '养生建议',
  `report_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详细报告内容',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1已完成 0进行中',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '测评时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_main_constitution`(`main_constitution`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '体质测评记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of assessment
-- ----------------------------
INSERT INTO `assessment` VALUES (1, 123, '{\"A\": 68.5, \"B\": 42.3, \"C\": 30.1, \"D\": 25.4, \"E\": 18.7, \"F\": 22.9, \"G\": 15.6, \"H\": 28.3, \"I\": 12.8}', 'A', '[\"B\", \"H\"]', '您属于平和质，总体健康状况良好。建议保持规律作息，均衡饮食，适量运动。兼有气虚和气郁倾向，注意避免劳累，保持心情愉悦。', NULL, 1, '2025-12-05 21:38:02', '2025-12-05 21:38:02');
INSERT INTO `assessment` VALUES (2, 123, '{\"A\": 45.2, \"B\": 72.8, \"C\": 38.5, \"D\": 32.1, \"E\": 25.6, \"F\": 28.9, \"G\": 22.4, \"H\": 41.7, \"I\": 18.3}', 'B', '[\"H\"]', '您属于气虚质，元气不足。建议多吃小米、山药、红枣等补气食物，避免过度劳累，适当练习八段锦、太极拳等舒缓运动。', NULL, 1, '2025-12-05 21:38:02', '2025-12-05 21:38:02');
INSERT INTO `assessment` VALUES (3, 456, '{\"A\": 38.9, \"B\": 45.6, \"C\": 78.2, \"D\": 28.7, \"E\": 32.4, \"F\": 26.8, \"G\": 24.1, \"H\": 36.5, \"I\": 20.9}', 'C', '[]', '您属于阳虚质，阳气不足。建议注意保暖，多吃温热食物如生姜、羊肉、桂圆，避免生冷寒凉，适当进行温和运动。', NULL, 1, '2025-12-05 21:38:02', '2025-12-05 21:38:02');
INSERT INTO `assessment` VALUES (4, 456, '{\"A\": 42.1, \"B\": 38.7, \"C\": 32.5, \"D\": 75.6, \"E\": 28.9, \"F\": 45.2, \"G\": 31.8, \"H\": 52.4, \"I\": 24.6}', 'D', '[\"F\", \"H\"]', '您属于阴虚质，阴液亏少。建议多吃银耳、百合、梨等滋阴食物，避免熬夜和辛辣食物，适当练习瑜伽、太极等放松运动。', NULL, 1, '2025-12-05 21:38:02', '2025-12-05 21:38:02');
INSERT INTO `assessment` VALUES (5, 789, '{\"A\": 0, \"B\": 0, \"C\": 0, \"D\": 0, \"E\": 0, \"F\": 0, \"G\": 0, \"H\": 0, \"I\": 0}', 'A', '[]', '', NULL, 0, '2025-12-05 21:38:02', '2025-12-05 21:38:02');

-- ----------------------------
-- Table structure for assessment_answer
-- ----------------------------
DROP TABLE IF EXISTS `assessment_answer`;
CREATE TABLE `assessment_answer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '答案ID',
  `assessment_id` bigint(20) NOT NULL COMMENT '测评记录ID',
  `question_id` bigint(20) NOT NULL COMMENT '题目ID',
  `option_id` bigint(20) NULL DEFAULT NULL COMMENT '选项ID（如果选择了具体选项）',
  `answer_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '答案值',
  `score` int(11) NOT NULL COMMENT '得分',
  `answer_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '答题时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_assessment_question`(`assessment_id`, `question_id`) USING BTREE,
  INDEX `idx_assessment_id`(`assessment_id`) USING BTREE,
  INDEX `idx_question_id`(`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测评答案详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of assessment_answer
-- ----------------------------
INSERT INTO `assessment_answer` VALUES (1, 1, 1, NULL, '4', 4, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (2, 1, 2, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (3, 1, 3, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (4, 1, 4, NULL, '1', 1, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (5, 1, 5, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (6, 1, 6, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (7, 1, 7, NULL, '4', 4, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (8, 1, 8, NULL, '1', 1, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (9, 1, 9, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (10, 1, 10, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (11, 2, 1, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (12, 2, 2, NULL, '5', 5, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (13, 2, 3, NULL, '4', 4, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (14, 2, 4, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (15, 2, 5, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (16, 2, 6, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (17, 2, 7, NULL, '3', 3, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (18, 2, 8, NULL, '2', 2, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (19, 2, 9, NULL, '4', 4, '2025-12-05 21:38:29');
INSERT INTO `assessment_answer` VALUES (20, 2, 10, NULL, '1', 1, '2025-12-05 21:38:29');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目内容',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目描述/说明',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `is_required` tinyint(4) NULL DEFAULT 1 COMMENT '是否必答：1是 0否',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除：1删除 0正常',
  `constitution_category_id` bigint(20) NULL DEFAULT NULL COMMENT '关联体质分类ID',
  `question_type` tinyint(1) NULL DEFAULT NULL COMMENT '题目类型',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sort_order`(`sort_order`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (2, '您容易疲乏吗？', NULL, 2, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:36', 0, 2, 1);
INSERT INTO `question` VALUES (3, '您说话声音低弱无力吗？', NULL, 3, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:38', 0, 3, 1);
INSERT INTO `question` VALUES (4, '您手脚发凉吗？', NULL, 4, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:39', 0, 3, 1);
INSERT INTO `question` VALUES (6, '您感到身体沉重吗？', NULL, 6, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:42', 0, 5, 1);
INSERT INTO `question` VALUES (7, '您面部或鼻部有油腻感吗？', NULL, 7, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:44', 0, 2, 1);
INSERT INTO `question` VALUES (8, '您的皮肤容易出现瘀斑吗？', NULL, 8, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:45', 0, 6, 1);
INSERT INTO `question` VALUES (10, '您容易过敏吗？', NULL, 10, 1, '2025-12-05 21:37:34', '2025-12-06 18:11:50', 0, 3, 1);

-- ----------------------------
-- Table structure for question_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `question_category_rel`;
CREATE TABLE `question_category_rel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_category_rel
-- ----------------------------
INSERT INTO `question_category_rel` VALUES (1, 1, 1);
INSERT INTO `question_category_rel` VALUES (2, 2, 2);
INSERT INTO `question_category_rel` VALUES (3, 3, 3);
INSERT INTO `question_category_rel` VALUES (4, 4, 3);
INSERT INTO `question_category_rel` VALUES (6, 6, 5);
INSERT INTO `question_category_rel` VALUES (7, 7, 2);
INSERT INTO `question_category_rel` VALUES (8, 8, 6);
INSERT INTO `question_category_rel` VALUES (9, 9, 7);
INSERT INTO `question_category_rel` VALUES (10, 10, 3);

-- ----------------------------
-- Table structure for question_option
-- ----------------------------
DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_id` bigint(20) NOT NULL COMMENT '关联的题目ID',
  `option_text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项文本',
  `option_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项值（用于计算）',
  `score` int(11) NULL DEFAULT 0 COMMENT '选项得分（如果题目类型是量表）',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '选项显示顺序',
  `is_correct` tinyint(4) NULL DEFAULT 0 COMMENT '是否为正确选项（用于测试题）',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项图片URL',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_question_id`(`question_id`) USING BTREE,
  INDEX `idx_sort_order`(`sort_order`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目选项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_option
-- ----------------------------
INSERT INTO `question_option` VALUES (1, 1, '没有', '1', 1, 1, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (2, 1, '很少', '2', 2, 2, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (3, 1, '有时', '3', 3, 3, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (4, 1, '经常', '4', 4, 4, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (5, 1, '总是', '5', 5, 5, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (6, 2, '没有', '1', 1, 1, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (7, 2, '很少', '2', 2, 2, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (8, 2, '有时', '3', 3, 3, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (9, 2, '经常', '4', 4, 4, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (10, 2, '总是', '5', 5, 5, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (11, 3, '没有', '1', 1, 1, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (12, 3, '很少', '2', 2, 2, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (13, 3, '有时', '3', 3, 3, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (14, 3, '经常', '4', 4, 4, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (15, 3, '总是', '5', 5, 5, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (16, 4, '没有', '1', 1, 1, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (17, 4, '很少', '2', 2, 2, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (18, 4, '有时', '3', 3, 3, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (19, 4, '经常', '4', 4, 4, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (20, 4, '总是', '5', 5, 5, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (21, 5, '没有', '1', 1, 1, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (22, 5, '很少', '2', 2, 2, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (23, 5, '有时', '3', 3, 3, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (24, 5, '经常', '4', 4, 4, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');
INSERT INTO `question_option` VALUES (25, 5, '总是', '5', 5, 5, 0, NULL, 1, '2025-12-05 21:37:34', '2025-12-05 21:37:34');

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '被阅读次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (6, 'xx', 'https://img.quanxiaoha.com/quanxiaoha/193dd1504ebb4f138085acb23619e0dd.jpg', '1111', '2025-08-17 23:41:48', '2025-08-17 23:41:48', 0, 3);
INSERT INTO `t_article` VALUES (7, 'xx', 'http://192.168.134.128:9000/weblog/ba4468148fa74053897ec130d6053a02.jpg', '1111', '2025-08-17 23:47:27', '2025-10-16 13:33:51', 0, 9);
INSERT INTO `t_article` VALUES (8, 'maven报错', 'http://192.168.134.128:9000/weblog/f170b371dd2c44c094d8326be6887382.png', 'maven依赖爆红问题', '2025-10-14 21:09:54', '2025-10-14 21:09:54', 0, 5);
INSERT INTO `t_article` VALUES (9, 'AOP面向切面编程', 'http://192.168.134.128:9000/weblog/1fcabf9504d14df7bc9be38d507d5d31.png', 'AOP切面编程，具体应用', '2025-10-16 13:31:00', '2025-10-16 13:31:00', 0, 9);
INSERT INTO `t_article` VALUES (10, 'redis的zset实现用户活跃度榜单', 'http://192.168.134.128:9000/weblog/e9330eb98eaa4471b45dab88664fbe47.png', '将用户在博客中的活动如浏览、点赞、收藏功能已分数的形式存入redis中，这个数据结构是hash，同时将该用户的操作对应分数加到当天或者当月的zset该用户的分数中，方便排序', '2025-10-16 23:00:50', '2025-10-16 23:00:50', 0, 4);

-- ----------------------------
-- Table structure for t_article_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category_rel`;
CREATE TABLE `t_article_category_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_article_id`(`article_id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章所属分类关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_category_rel
-- ----------------------------
INSERT INTO `t_article_category_rel` VALUES (4, 6, 1);
INSERT INTO `t_article_category_rel` VALUES (14, 8, 1);
INSERT INTO `t_article_category_rel` VALUES (15, 9, 1);
INSERT INTO `t_article_category_rel` VALUES (16, 7, 1);
INSERT INTO `t_article_category_rel` VALUES (17, 10, 1);

-- ----------------------------
-- Table structure for t_article_content
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '教程正文',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_content
-- ----------------------------
INSERT INTO `t_article_content` VALUES (6, 6, '## 👋 自我介绍\r\n\r\n> 大家好，我是小哈。前某厂中台架构，公众号小哈学 Java 作者。91年生人，码龄 9 年，先后供职于支付、共享等互联网领域，主导负责过数据传输、日志平台、任务调度、文件平台等产品，以支撑各部门业务线。喜欢分享知识，热爱技术，也不止于技术，不只是写 Java，业余也爱玩前端、Python、Android 等，是个活跃的技术折腾者。\r\n\r\n本实战项目是星球内部第一个项目，目前**已输出 17w+ 字，更新了 124 小节内容，演示图片：802 张，还在持续爆肝中，目标是将 Java 程序员生涯中，比较典型的项目都教会大家，如前后端分离博客全栈级开发、秒杀系统、在线商城、IM 即时通讯（Netty）、权限管理（Spring Security）、Spring Cloud Alibaba 微服务等等... 目前有 370+ 小伙伴已加入，一起学习打卡，一起进步！同频的人才能走的更快、更远 ！** 欢迎各位小伙伴加入哟~\r\n\r\n👉 [加入星球](https://t.zsxq.com/12h3hIayK \"加入星球\")，你将获得: <span style=\"color: #f97316; font-weight: 700;\">专属的项目实战 / Java 学习路线 / 一对一提问 / 学习打卡 / 赠书活动</span>，与一群热爱学习的小伙伴一起，将走的更快、更远! **公众号：小哈学Java, 回复【星球】**，可领取<span style=\"color: #f97316; font-weight: 700;\">专属优惠券</span>~\r\n\r\n---\r\n\r\n> 本项目 1.0 版本已部署到了阿里云服务器上，可点击下面链接进行访问，查看实际效果：\r\n>\r\n> 演示地址：[http://118.31.41.16/](http://118.31.41.16/)\r\n>\r\n> 后台登录演示账号:\r\n> \r\n> - 账号：test\r\n> - 密码：test\r\n\r\n## 🏃 关于实战项目\r\n\r\n知识星球是个私密学习圈子，我会在星球内部，出**一系列从 0 到 1 的实战项目，贴合真实的企业级项目开发规范，使用主流的企业技术栈，全程手写后端 + 前端完整代码，通过专栏的形式，把每个功能点的开发的步骤，手摸手，通过丰富的图片 + 文字，保姆级教学（PS: 同时按小节进度提供代码，不至于一上来代码量太多，不知道从哪入手）**。\r\n\r\n\r\n![](https://img.quanxiaoha.com/quanxiaoha/169361945065538)\r\n\r\n目前，我已经给自己的网站：[犬小哈教程](www.quanxiaoha.com/column \"犬小哈教程\") 新开发了专栏模块，可以让小伙伴们只需跟着实战专栏，按照章节顺序教学来，上手敲，即可搞定每个功能点的开发，成体系地完成一个独立项目。*目前加入的小伙伴，都给出了超高评价，以下了截取了部分好评*：\r\n\r\n![](https://img.quanxiaoha.com/quanxiaoha/169733756405612)\r\n\r\n![](https://img.quanxiaoha.com/quanxiaoha/169733761293187)\r\n\r\n![](https://img.quanxiaoha.com/quanxiaoha/169733762195775)\r\n\r\n另外，在跟随小节内容上手的过程中，若遇到问题，可在星球内发起 *1v1 提问，小哈亲自解答*。\r\n\r\n![\"星球内提问\"](https://img.quanxiaoha.com/quanxiaoha/169396126861858 \"星球内提问\")\r\n\r\n星球说不清楚的，项目进度因为某一块搞不定的，微信发我源码，帮忙看问题出在哪：\r\n\r\n![搞不定的，微信发我源码，帮忙看问题出在哪](https://img.quanxiaoha.com/quanxiaoha/169406285385964 \"搞不定的，微信发我源码，帮忙看问题出在哪\")\r\n\r\n\r\n陪伴式写项目，到最终部署到云服务器上，能够通过域名来访问，完成项目上线。\r\n\r\n> 💡 TIP : 后期也会尝试分享一些适合程序员的技术副业，比如开发一些小工具网站，进行推广运营，有了一定用户量，能够挣点零花钱啥的。当然，这都是后话了，前提还需要你能够自行完整的开发一个独立应用，前期还是以项目实战为主。\r\n\r\n既然小哈是准备出一系列的实战项目，我希望这些项目的难度是循序渐进的，能够让你真实的感受到自己的功力在慢慢增强。但是又不想写那种纯纯的 CRUD 型管理后台项目，太枯燥。那么，第一个项目小哈就定位在难度不大，易上手，有趣，并且非常有代表性，实际工作中也能够被频繁用到的。\r\n\r\n脑瓜子一转，想到之前好多读者问我博客的事情，今年 4 月份的时候，又有读者微信问我: *你的博客有没有开源，感觉还挺好看，也想学习、部署一个。*\r\n\r\n![](https://img.quanxiaoha.com/quanxiaoha/169355366112215)\r\n\r\n于是乎，花了点时间整了第一个实战项目 —— **前后端分离的博客 Weblog**。\r\n\r\n\r\n## 💁 项目介绍\r\n\r\n每个技术人都应该有属于自己的博客！相比较直接使用第三方博客平台，自行搭建博客更有成就感；另外就是没有平台限制，比如你想发个二维码引流啥的，平台基本都是不允许的，还有，你可以自由 `div` 定制自己想要的博客 `css` 样式，哪天 UI 看不爽了，咱就自己换；最后，*面试的时候，如果简历贴上的是自己开发博客地址，也会很加分*！\r\n\r\n### 🔗 演示地址\r\n\r\n目前 1.0 版本已经部署到了阿里云服务器上，可点击下面链接进行访问，查看实际效果：\r\n\r\n[http://118.31.41.16/](http://118.31.41.16/ \"http://118.31.41.16/\")\r\n\r\n管理后台登录账号/密码:\r\n\r\n- 账号：test\r\n- 密码：test\r\n\r\n> ⚠️ 注意：该账号的角色为*游客*角色，*仅支持查询操作*，新增、修改、删除操作会提示不允许。\r\n\r\n### ⚒️ 功能模块\r\n\r\n> 💡 TIP : 以下*只是 1.0 版本的功能，后续小哈将添加更多功能进去, 比如图库管理、知识库、在线人数统计、SSR（服务端渲染） 等等*，能够想到的高逼格功能，咱都整上，附带超详细的实战图文笔记 ...\r\n\r\n![Weblog 功能模块一览](https://img.quanxiaoha.com/quanxiaoha/169560157482464 \"Weblog 功能模块一览\")\r\n\r\n### ✏️ 技术栈\r\n\r\n![Weblog 技术栈一览](https://img.quanxiaoha.com/quanxiaoha/169560181378937 \"Weblog 技术栈一览\")\r\n\r\n## 🎉 专栏目标\r\n\r\n学完本项目，你将具备如下能力：\r\n\r\n- 掌握独立开发全栈项目的能力（*后端 + 前端*）；\r\n- 掌握 Spring Boot 相关技术栈，以及构建后端项目能力，写出符合企业级的代码规范；\r\n- 掌握 Vue 3.2 + Element Plus + Vite 4 技术构建前端工程的能力，并能够手动搭建 Admin 后台管理系统；\r\n- 掌握前端页面响应式设计（同时适配不同屏幕），排版布局，能够根据自己需求，`div` 自己想要的前端效果；\r\n- ...\r\n\r\n## 💡 专栏亮点\r\n\r\n- 在技术选型上，小哈选择了目前主流热门的技术栈，对标企业级项目开发；\r\n- 严格把控代码质量，数据库设计，写出令同事称道的代码；\r\n- 熟悉后端工程的搭建，如一些通用的基础设施：参数校验、全局异常捕获、`API` 统一出入参日志打印等等；\r\n- 能够独立完成整个网站的部署流程，从功能开发到服务器、域名选购，再到网站备案，最终公网可访问；\r\n- 对象存储 `Minio` 的使用, 能够独立搭建个人图床；\r\n- 从 0 到 1 ，通过 `Element Plus` 纯手搭 `Admin` 管理后台前端骨架；\r\n- 使用 Vue 3 `setup` 等语法糖新特性；\r\n- 博客前台页面在设计上美观大气；\r\n- ...\r\n\r\n## 📖 专栏大纲\r\n\r\n整个实战专栏，小哈按功能点开发进度来做的目录，目前已经更新到了第第五章，目录大致如下：\r\n\r\n> 💡 TIP : 如下目录不代表最终内容，只会更多，目前只是把已完成的部分详细的罗列了出来，其中大部分功能正在开发中，所属具体小节的标题也会陆续更新进来。\r\n\r\n- 一、[项目介绍](https://www.quanxiaoha.com/column/10000.html)\r\n- 二、开发环境搭建\r\n  - [2.1 【后端】环境安装&工具准备](https://www.quanxiaoha.com/column/10003.html)\r\n  - [2.2 【前端】开发环境&工具安装](https://www.quanxiaoha.com/column/10004.html)\r\n\r\n- 三、Spring Boot 后端工程搭建\r\n  - [3.1 搭建 Spring Boot 多模块工程](https://www.quanxiaoha.com/column/10005.html)\r\n  - [3.2 Spring Boot 多环境配置](https://www.quanxiaoha.com/column/10006.html)\r\n  - [3.3 配置 Lombok](https://www.quanxiaoha.com/column/10007.html)\r\n  - [3.4 Spring Boot 整合 Lockback 日志](https://www.quanxiaoha.com/column/10008.html)\r\n  - [3.5 Spring Boot 自定义注解，实现 API 请求日志切面](https://www.quanxiaoha.com/column/10009.html)\r\n  - [3.6 Spring Boot 通过 MDC 实现日志跟踪](https://www.quanxiaoha.com/column/10010.html)\r\n  - [3.7 Spring Boot 实现优雅的参数校验](https://www.quanxiaoha.com/column/10011.html)\r\n  - [3.8 Spring Boot 自定义响应工具类](https://www.quanxiaoha.com/column/10012.html)\r\n  - [3.9 Spring Boot 实现全局异常管理](https://www.quanxiaoha.com/column/10013.html)\r\n  - [3.10 全局异常处理器+参数校验（最佳实践）](https://www.quanxiaoha.com/column/10014.html)\r\n  - [3.11 整合 Knife4j：提升接口调试效率](https://www.quanxiaoha.com/column/10015.html)\r\n  - [3.12 自定义 Jackson 序列化、反序列化，支持 Java 8 日期新特性](https://www.quanxiaoha.com/column/10016.html)\r\n  - [3.13 小结](https://www.quanxiaoha.com/column/10017.html)\r\n\r\n- 四、使用 Vue 3 + Vite 4 搭建前端工程\r\n  - [4.1 Vue 3 环境安装& Weblog 项目搭建](https://www.quanxiaoha.com/column/10018.html)\r\n  - [4.2 安装 VSCode 开发工具](https://www.quanxiaoha.com/column/10019.html)\r\n  - [4.3 添加 vue-router 路由管理器](https://www.quanxiaoha.com/column/10020.html)\r\n  - [4.4 Vite 配置路径别名：更方便的引用文件](https://www.quanxiaoha.com/column/10021.html)\r\n  - [4.5 整合 Tailwind CSS](https://www.quanxiaoha.com/column/10022.html)\r\n  - [4.6 整合 Tailwind CSS 组件库：Flowbite](https://www.quanxiaoha.com/column/10023.html)\r\n  - [4.7 整合饿了么 Element Plus 组件库](https://www.quanxiaoha.com/column/10024.html)\r\n\r\n- 五、登录模块开发\r\n  - [5.1 登录页设计：支持响应式布局](https://www.quanxiaoha.com/column/10025.html)\r\n  - [5.2 登录页加点盐：通过 Animate.css 添加动画](https://www.quanxiaoha.com/column/10026.html)\r\n  - [5.3 整合 Mybatis Plus](https://www.quanxiaoha.com/column/10027.html)\r\n  - [5.4 p6spy 组件打印完整的 SQL 语句、执行耗时](https://www.quanxiaoha.com/column/10028.html)\r\n  - [5.5 整合 Spring Security](https://www.quanxiaoha.com/column/10029.html)\r\n  - [5.6 Spring Security 整合 JWT ：实现身份认证](https://www.quanxiaoha.com/column/10030.html)\r\n  - [5.7 Spring Security 整合 JWT ：实现接口鉴权](https://www.quanxiaoha.com/column/10031.html)\r\n  - [5.8 Vue 整合 Axios 实现登录功能](https://www.quanxiaoha.com/column/10032.html)\r\n  - [5.9 登录页表单验证](https://www.quanxiaoha.com/column/10033.html)\r\n  - [5.10 登录消息提示、回车键监听、按钮加载 Loading](https://www.quanxiaoha.com/column/10034.html)\r\n  - [5.11 存储 Token 到 Cookie 中](https://www.quanxiaoha.com/column/10035.html)\r\n  - [5.12 Axios 添加请求拦截器、响应拦截器](https://www.quanxiaoha.com/column/10036.html)\r\n  - [5.13 全局路由拦截：实现页面标题动态设置、后台路由跳转的登录判断](https://www.quanxiaoha.com/column/10037.html)\r\n  - [5.14 实现页面顶部加载 Loading 效果](https://www.quanxiaoha.com/column/10038.html)\r\n  - [5.15 重复登录问题优化、密码框可显示密码](https://www.quanxiaoha.com/column/10040.html)\r\n  - [5.16 角色鉴权：添加演示账号，仅支持查询操作](https://www.quanxiaoha.com/column/10089.html)\r\n  \r\n  \r\n  \r\n\r\n- 六、Element Plus 手搭 Admin 管理后台骨架\r\n  - [6.1 搭建管理后台基本布局](https://www.quanxiaoha.com/column/10039.html)\r\n  - [6.2 后台公共 Header 头：样式布局](https://www.quanxiaoha.com/column/10041.html)\r\n  - [6.3 后台公共左侧 Menu 菜单栏：样式布局](https://www.quanxiaoha.com/column/10042.html)\r\n  - [6.4 整合全局状态管理库 Pinia](https://www.quanxiaoha.com/column/10043.html)\r\n  - [6.5 左边菜单栏点击收缩、展开功能实现](https://www.quanxiaoha.com/column/10044.html)\r\n  - [6.6 支持全屏展示、页面点击刷新](https://www.quanxiaoha.com/column/10045.html)\r\n  - [6.7 标签导航栏组件实现：样式布局](https://www.quanxiaoha.com/column/10046.html)\r\n  - [6.8 标签导航栏组件实现：路由同步 (1)](https://www.quanxiaoha.com/column/10047.html)\r\n  - [6.9 标签导航栏组件实现：路由同步 (2)](https://www.quanxiaoha.com/column/10048.html)\r\n  - [6.10 标签导航栏组件实现：标签页关闭](https://www.quanxiaoha.com/column/10049.html)\r\n  - [6.11 标签导航栏组件实现：关闭其他、全部标签页](https://www.quanxiaoha.com/column/10050.html)\r\n  - [6.12 后台公共 Footer 页脚：样式布局](https://www.quanxiaoha.com/column/10051.html)\r\n  - [6.13 使用 KeepAlive 缓存组件，提高页面切换性能和响应速度](https://www.quanxiaoha.com/column/10052.html)\r\n  - [6.14 使用 Transition 组件添加全局过渡动画](https://www.quanxiaoha.com/column/10053.html)\r\n  - [6.15 修改用户密码接口开发](https://www.quanxiaoha.com/column/10054.html)\r\n  - [6.16 获取当前登录用户信息接口开发](https://www.quanxiaoha.com/column/10055.html)\r\n  - [6.17 Pinia 存储用户信息，动态显示登录用户名](https://www.quanxiaoha.com/column/10056.html)\r\n  - [6.18 使用 pinia-persist 插件实现 Pinia 数据持久化](https://www.quanxiaoha.com/column/10057.html)\r\n  - [6.19 用户修改密码、退出登录功能开发](https://www.quanxiaoha.com/column/10058.html)\r\n  - [6.20 小结](https://www.quanxiaoha.com/column/10059.html)\r\n\r\n  \r\n\r\n  \r\n- 七、管理后台：文章分类模块开发\r\n  - [7.1 分类模块接口分析](https://www.quanxiaoha.com/column/10060.html)\r\n  - [7.2 文章分类：新增接口开发](https://www.quanxiaoha.com/column/10061.html)\r\n  - [7.3 文章分类：分页接口开发](https://www.quanxiaoha.com/column/10062.html)\r\n  - [7.4 文章分类：删除接口开发](https://www.quanxiaoha.com/column/10063.html)\r\n  - [7.5 文章发布：分类 Select 下拉列表接口开发](https://www.quanxiaoha.com/column/10064.html)\r\n  - [7.6 后台分类管理页面：样式布局](https://www.quanxiaoha.com/column/10065.html)\r\n  - [7.7 Config Provider 全局配置: 实现组件中文化](https://www.quanxiaoha.com/column/10066.html)\r\n  - [7.8 文章分类：分页列表数据动态渲染](https://www.quanxiaoha.com/column/10067.html)\r\n  - [7.9 文章分类：新增功能开发](https://www.quanxiaoha.com/column/10068.html)\r\n  - [7.10 文章分类：删除功能开发](https://www.quanxiaoha.com/column/10069.html)\r\n  - [7.11 通用表单对话框组件封装](https://www.quanxiaoha.com/column/10070.html)\r\n  - [7.12 添加 Table 组件加载 Loading 、表单对话框提交按钮 Loading 动画](https://www.quanxiaoha.com/column/10071.html)\r\n  \r\n\r\n\r\n- 八、管理后台：标签模块开发\r\n  - [8.1 标签模块接口分析【视频讲解】](https://www.quanxiaoha.com/column/10072.html)\r\n  - [8.2 标签管理：新增标签接口开发【视频讲解】](https://www.quanxiaoha.com/column/10073.html)\r\n  - [8.3 标签管理：标签分页接口开发【视频讲解】](https://www.quanxiaoha.com/column/10074.html)\r\n  - [8.4 标签管理：删除标签接口开发【视频讲解】](https://www.quanxiaoha.com/column/10075.html)\r\n  - [8.5 标签关键词模糊查询 select 列表接口开发【视频讲解】](https://www.quanxiaoha.com/column/10076.html)\r\n  - [8.6 标签管理页面开发：分页列表【视频讲解】](https://www.quanxiaoha.com/column/10077.html)\r\n  - [8.7 标签管理页面开发：新增&删除标签功能【视频讲解】](https://www.quanxiaoha.com/column/10078.html)\r\n  \r\n  \r\n  \r\n- 九、管理后台：博客设置模块开发\r\n  - [9.1 博客设置模块功能分析、表设计](https://www.quanxiaoha.com/column/10079.html)\r\n  - [9.2 Docker 本地安装 Minio 对象存储](https://www.quanxiaoha.com/column/10080.html)\r\n  - [9.3 文件上传接口开发](https://www.quanxiaoha.com/column/10081.html)\r\n  - [9.4 博客设置: 更新接口开发](https://www.quanxiaoha.com/column/10082.html)\r\n  - [9.5 整合 Mapstruct : 简化属性映射](https://www.quanxiaoha.com/column/10083.html)\r\n  - [9.6 博客设置：获取详情接口开发](https://www.quanxiaoha.com/column/10084.html)\r\n  - [9.7 博客设置页面：样式布局](https://www.quanxiaoha.com/column/10085.html)\r\n  - [9.8 管理后台：滚动样式优化](https://www.quanxiaoha.com/column/10086.html)\r\n  - [9.9 博客设置页：数据渲染、图片上传](https://www.quanxiaoha.com/column/10087.html)\r\n  - [9.10 博客设置页：更新设置](https://www.quanxiaoha.com/column/10088.html)\r\n  \r\n\r\n\r\n\r\n- 十、管理后台：文章模块开发\r\n  - [10.1 文章管理模块功能分析、表设计](https://www.quanxiaoha.com/column/10090.html)\r\n  - [10.2 文章管理：文章发布接口开发（1）](https://www.quanxiaoha.com/column/10091.html)\r\n  - [10.3 文章管理：文章发布接口开发（2）—— SQL 注入器实现批量插入](https://www.quanxiaoha.com/column/10092.html)\r\n  - [10.4 文章管理：文章删除接口开发](https://www.quanxiaoha.com/column/10093.html)\r\n  - [10.5 文章管理：分页接口开发](https://www.quanxiaoha.com/column/10094.html)\r\n  - [10.6 文章管理：获取文章详情接口开发](https://www.quanxiaoha.com/column/10095.html)\r\n  - [10.7 文章管理：文章更新接口开发](https://www.quanxiaoha.com/column/10096.html)\r\n  - [10.8 文章管理：分页列表开发](https://www.quanxiaoha.com/column/10097.html)\r\n  - [10.9 文章管理页：删除文章开发](https://www.quanxiaoha.com/column/10098.html)\r\n  - [10.10 文章管理页：写文章对话框样式布局](https://www.quanxiaoha.com/column/10099.html)\r\n  - [10.11 文章管理页：文章发布功能开发](https://www.quanxiaoha.com/column/10100.html)\r\n  - [10.12 文章管理：获取所有标签 Select 列表接口开发](https://www.quanxiaoha.com/column/10101.html)\r\n  - [10.13 文章管理页：文章编辑功能开发](https://www.quanxiaoha.com/column/10102.html)\r\n  - [10.14 Bug 修复：分类、标签删除接口添加是否关联文章校验; 前端 token 过期问题 fixed](https://www.quanxiaoha.com/column/10103.html)\r\n  \r\n  \r\n  \r\n  \r\n  \r\n\r\n- 十一、博客前台：首页开发\r\n   - [11.1 前台首页、归档页接口分析](https://www.quanxiaoha.com/column/10104.html)\r\n   - [11.2 前台首页：文章分页接口开发](https://www.quanxiaoha.com/column/10105.html)\r\n   - [11.3 公共侧边栏：获取分类、标签列表接口开发](https://www.quanxiaoha.com/column/10106.html)\r\n   - [11.4 公共部分：获取博客设置信息接口开发](https://www.quanxiaoha.com/column/10107.html)\r\n   - [11.5 前台 Header 头组件封装](https://www.quanxiaoha.com/column/10108.html)   \r\n   - [11.6 首页样式布局设计（1）](https://www.quanxiaoha.com/column/10109.html)\r\n   - [11.7 首页样式布局设计（2） —— 侧边栏博主信息卡片](https://www.quanxiaoha.com/column/10110.html)\r\n   - [11.8 首页样式布局设计（3） —— 侧边栏分类、标签卡片](https://www.quanxiaoha.com/column/10111.html)\r\n   - [11.9 首页样式布局设计（4） —— Footer 组件封装](https://www.quanxiaoha.com/column/10112.html)\r\n   - [11.10 首页文章分页数据渲染](https://www.quanxiaoha.com/column/10113.html)\r\n   - [11.11 公共右边栏：博主信息卡片组件封装](https://www.quanxiaoha.com/column/10114.html)\r\n   - [11.12 公共右边栏：分类、标签卡片组件封装](https://www.quanxiaoha.com/column/10115.html)\r\n   - [11.13 公共 Header 头：跳转后台、退出登录功能开发](https://www.quanxiaoha.com/column/10116.html)\r\n   \r\n   \r\n\r\n- 十二、博客前台：归档列表页、分类列表页、标签列表页开发\r\n   - [12.1 归档页、分类列表页接口分析](https://www.quanxiaoha.com/column/10117.html)\r\n   - [12.2 文章归档分页接口开发](https://www.quanxiaoha.com/column/10118.html)\r\n   - [12.3 前台归档页：样式布局设计](https://www.quanxiaoha.com/column/10119.html)\r\n   - [12.4 前台归档页：分页列表功能开发](https://www.quanxiaoha.com/column/10120.html)\r\n   - [12.5 前台分类页开发](https://www.quanxiaoha.com/column/10121.html)\r\n   - [12.6 获取某个分类下的文章列表——分页接口开发](https://www.quanxiaoha.com/column/10122.html)\r\n   - [12.7 前台分类-文章列表页: 样式布局开发](https://www.quanxiaoha.com/column/10123.html)\r\n   - [12.8 分类-文章列表页开发](https://www.quanxiaoha.com/column/10124.html)\r\n   - [12.9 前台标签列表页：样式布局&功能开发](https://www.quanxiaoha.com/column/10125.html)\r\n   - [12.10 获取某个标签下的文章列表——分页接口开发](https://www.quanxiaoha.com/column/10130.html)\r\n   - [12.11 标签-文章列表页开发](https://www.quanxiaoha.com/column/10131.html)\r\n\r\n\r\n- 十三、博客前台：文章详情页开发\r\n   - [13.1 文章详情页接口分析](https://www.quanxiaoha.com/column/10126.html)\r\n   - [13.2 后端封装 Markdown 装换工具类](https://www.quanxiaoha.com/column/10127.html)\r\n   - [13.3 获取文章详情接口开发](https://www.quanxiaoha.com/column/10128.html)\r\n   - [13.4 文章详情页：样式布局设计](https://www.quanxiaoha.com/column/10129.html)\r\n\r\n   - *努力爆肝中，每天更新两小节, 按目前的更新速度，1.0 版本差不多还剩1个半月更新完毕...*\r\n- 十四、管理后台：仪表盘模块开发\r\n- 十五、项目部署上线\r\n  - 云服务器选购\r\n  - 相关环境安装（JDK、Docker、Nginx、Mysql）\r\n  - Nginx 配合 Spring Boot 部署\r\n  - 部署前端项目以及通过 IP 访问\r\n  - 域名选购\r\n  - 网站备案\r\n  - 域名映射，项目正式上线\r\n\r\n\r\n\r\n## 👨🏻‍💻 适用人群\r\n\r\n- **在校学生**，有一定基础，想做毕业设计，或者为找工作准备，需要实战项目加分；\r\n\r\n  > 💡 TIP: 小白也没关系，小哈将会告诉你学习路线是啥，哪里有免费的高质量学习视频可以白嫖，学完这些技术栈后再来做实战项目，或者学一点基础边实战边学习都可以。\r\n\r\n- **已经参与工作，对前后端分离感兴趣**，想学习 Vue 3 前端，对独立上线自己网站感兴趣的童鞋；\r\n- **想独立接私活**，需要同时会后端、前端技术栈的童鞋；\r\n\r\n## ✊ 如何加入？\r\n\r\n小哈已经将本站的专栏模块接入了知识星球，想要查看专栏内容，需要订阅我星球后，*微信扫码授权登录后即可解锁所有内容*。因为目前也是刚开始运营，所以价格不会太高，星球官方定价最低必须是 50 元。小哈最终定价为 <font class=\"text-xl\" style=\'color: red\'><b>限时 35 元（附 15 元的优惠券，记得扫码领取下方优惠券加入哟）</b></font>，后续随着内容慢慢的更新迭代，会慢慢涨上去，所以早加入更具性价比哟~ \r\n\r\n<font class=\"text-xl\" style=\'color: red\'><b>星球支持 3 天无理由退费</b></font>，感兴趣的小伙伴*可先加入，看看内容质量如何，不合适直接退款就行，觉得确实内容很干货，就留下来学习，无套路!*\r\n\r\n<div class=\"flex items-center justify-center text-lg text-red-500 font-bold mb-2\">扫描下方二维码加入, 星球支持 3 天无理由退款，可以先进去看看合不合适👇👇</div>\r\n\r\n![\"领取优惠券加入，更划算\"](https://img.quanxiaoha.com/quanxiaoha/169355760680941 \"领取优惠券加入，更划算\")\r\n\r\n<div class=\"flex items-center justify-center text-lg text-red-500 font-bold\">扫描上方二维码加入, 星球支持 3 天无理由退款，可以先进去看看合不合适👆👆</div>\r\n\r\n\r\n\r\n\r\n## ❓ 关于答疑\r\n\r\n小伙伴们如果在跟着专栏学习，手敲项目的过程中遇到问题，碰到无法解决的问题，**可在小哈的知识星球内部提问**，我会统一来解答, 如果星球说不清楚的，就加私人微信，打包发项目，亲自给你看哪一步有问题，保证跟上项目进度，不落下任何一个小伙伴，大家一起冲冲冲~\r\n\r\n## 😃 加微信咨询\r\n\r\n对专栏感兴趣的小伙伴，也可以加小哈私人微信来咨询，扫描下方二维码即可，记得备注【*咨询*】哟：\r\n\r\n![扫描二维码，添加小哈私人微信](https://img.quanxiaoha.com/quanxiaoha/169536889316499 \"扫描二维码，添加小哈私人微信\")\r\n');
INSERT INTO `t_article_content` VALUES (7, 7, '## 👋 自我介绍\n\n> 大家好，我是小哈。前某厂中台架构，公众号小哈学 Java 作者。91年生人，码龄 9 年，先后供职于支付、共享等互联网领域，主导负责过数据传输、日志平台、任务调度、文件平台等产品，以支撑各部门业务线。喜欢分享知识，热爱技术，也不止于技术，不只是写 Java，业余也爱玩前端、Python、Android 等，是个活跃的技术折腾者。\n\n\n```language\n本实战项目是星球内部第一个项目，目前**已输出 17w+ 字，更新了 124 小节内容，演示图片：802 张，还在持续爆肝中，目标是将 Java 程序员生涯中，比较典型的项目都教会大家，如前后端分离博客全栈级开发、秒杀系统、在线商城、IM 即时通讯（Netty）、权限管理（Spring Security）、Spring Cloud Alibaba 微服务等等... 目前有 370+ 小伙伴已加入，一起学习打卡，一起进步！同频的人才能走的更快、更远 ！** 欢迎各位小伙伴加入哟~\n```\n\n\n👉 [加入星球](https://t.zsxq.com/12h3hIayK \"加入星球\")，你将获得: <span style=\"color: #f97316; font-weight: 700;\">专属的项目实战 / Java 学习路线 / 一对一提问 / 学习打卡 / 赠书活动</span>，与一群热爱学习的小伙伴一起，将走的更快、更远! **公众号：小哈学Java, 回复【星球】**，可领取<span style=\"color: #f97316; font-weight: 700;\">专属优惠券</span>~\n\n---\n\n> 本项目 1.0 版本已部署到了阿里云服务器上，可点击下面链接进行访问，查看实际效果：\n>\n> 演示地址：[http://118.31.41.16/](http://118.31.41.16/)\n>\n> 后台登录演示账号:\n> \n> - 账号：test\n> - 密码：test\n\n## 🏃 关于实战项目\n\n知识星球是个私密学习圈子，我会在星球内部，出**一系列从 0 到 1 的实战项目，贴合真实的企业级项目开发规范，使用主流的企业技术栈，全程手写后端 + 前端完整代码，通过专栏的形式，把每个功能点的开发的步骤，手摸手，通过丰富的图片 + 文字，保姆级教学（PS: 同时按小节进度提供代码，不至于一上来代码量太多，不知道从哪入手）**。\n\n\n![](https://img.quanxiaoha.com/quanxiaoha/169361945065538)\n\n目前，我已经给自己的网站：[犬小哈教程](www.quanxiaoha.com/column \"犬小哈教程\") 新开发了专栏模块，可以让小伙伴们只需跟着实战专栏，按照章节顺序教学来，上手敲，即可搞定每个功能点的开发，成体系地完成一个独立项目。*目前加入的小伙伴，都给出了超高评价，以下了截取了部分好评*：\n\n![](https://img.quanxiaoha.com/quanxiaoha/169733756405612)\n\n![](https://img.quanxiaoha.com/quanxiaoha/169733761293187)\n\n![](https://img.quanxiaoha.com/quanxiaoha/169733762195775)\n\n另外，在跟随小节内容上手的过程中，若遇到问题，可在星球内发起 *1v1 提问，小哈亲自解答*。\n\n![\"星球内提问\"](https://img.quanxiaoha.com/quanxiaoha/169396126861858 \"星球内提问\")\n\n星球说不清楚的，项目进度因为某一块搞不定的，微信发我源码，帮忙看问题出在哪：\n\n![搞不定的，微信发我源码，帮忙看问题出在哪](https://img.quanxiaoha.com/quanxiaoha/169406285385964 \"搞不定的，微信发我源码，帮忙看问题出在哪\")\n\n\n陪伴式写项目，到最终部署到云服务器上，能够通过域名来访问，完成项目上线。\n\n> 💡 TIP : 后期也会尝试分享一些适合程序员的技术副业，比如开发一些小工具网站，进行推广运营，有了一定用户量，能够挣点零花钱啥的。当然，这都是后话了，前提还需要你能够自行完整的开发一个独立应用，前期还是以项目实战为主。\n\n既然小哈是准备出一系列的实战项目，我希望这些项目的难度是循序渐进的，能够让你真实的感受到自己的功力在慢慢增强。但是又不想写那种纯纯的 CRUD 型管理后台项目，太枯燥。那么，第一个项目小哈就定位在难度不大，易上手，有趣，并且非常有代表性，实际工作中也能够被频繁用到的。\n\n脑瓜子一转，想到之前好多读者问我博客的事情，今年 4 月份的时候，又有读者微信问我: *你的博客有没有开源，感觉还挺好看，也想学习、部署一个。*\n\n![](https://img.quanxiaoha.com/quanxiaoha/169355366112215)\n\n于是乎，花了点时间整了第一个实战项目 —— **前后端分离的博客 Weblog**。\n\n\n## 💁 项目介绍\n\n每个技术人都应该有属于自己的博客！相比较直接使用第三方博客平台，自行搭建博客更有成就感；另外就是没有平台限制，比如你想发个二维码引流啥的，平台基本都是不允许的，还有，你可以自由 `div` 定制自己想要的博客 `css` 样式，哪天 UI 看不爽了，咱就自己换；最后，*面试的时候，如果简历贴上的是自己开发博客地址，也会很加分*！\n\n### 🔗 演示地址\n\n目前 1.0 版本已经部署到了阿里云服务器上，可点击下面链接进行访问，查看实际效果：\n\n[http://118.31.41.16/](http://118.31.41.16/ \"http://118.31.41.16/\")\n\n管理后台登录账号/密码:\n\n- 账号：test\n- 密码：test\n\n> ⚠️ 注意：该账号的角色为*游客*角色，*仅支持查询操作*，新增、修改、删除操作会提示不允许。\n\n### ⚒️ 功能模块\n\n> 💡 TIP : 以下*只是 1.0 版本的功能，后续小哈将添加更多功能进去, 比如图库管理、知识库、在线人数统计、SSR（服务端渲染） 等等*，能够想到的高逼格功能，咱都整上，附带超详细的实战图文笔记 ...\n\n![Weblog 功能模块一览](https://img.quanxiaoha.com/quanxiaoha/169560157482464 \"Weblog 功能模块一览\")\n\n### ✏️ 技术栈\n\n![Weblog 技术栈一览](https://img.quanxiaoha.com/quanxiaoha/169560181378937 \"Weblog 技术栈一览\")\n\n## 🎉 专栏目标\n\n学完本项目，你将具备如下能力：\n\n- 掌握独立开发全栈项目的能力（*后端 + 前端*）；\n- 掌握 Spring Boot 相关技术栈，以及构建后端项目能力，写出符合企业级的代码规范；\n- 掌握 Vue 3.2 + Element Plus + Vite 4 技术构建前端工程的能力，并能够手动搭建 Admin 后台管理系统；\n- 掌握前端页面响应式设计（同时适配不同屏幕），排版布局，能够根据自己需求，`div` 自己想要的前端效果；\n- ...\n\n## 💡 专栏亮点\n\n- 在技术选型上，小哈选择了目前主流热门的技术栈，对标企业级项目开发；\n- 严格把控代码质量，数据库设计，写出令同事称道的代码；\n- 熟悉后端工程的搭建，如一些通用的基础设施：参数校验、全局异常捕获、`API` 统一出入参日志打印等等；\n- 能够独立完成整个网站的部署流程，从功能开发到服务器、域名选购，再到网站备案，最终公网可访问；\n- 对象存储 `Minio` 的使用, 能够独立搭建个人图床；\n- 从 0 到 1 ，通过 `Element Plus` 纯手搭 `Admin` 管理后台前端骨架；\n- 使用 Vue 3 `setup` 等语法糖新特性；\n- 博客前台页面在设计上美观大气；\n- ...\n\n## 📖 专栏大纲\n\n整个实战专栏，小哈按功能点开发进度来做的目录，目前已经更新到了第第五章，目录大致如下：\n\n> 💡 TIP : 如下目录不代表最终内容，只会更多，目前只是把已完成的部分详细的罗列了出来，其中大部分功能正在开发中，所属具体小节的标题也会陆续更新进来。\n\n- 一、[项目介绍](https://www.quanxiaoha.com/column/10000.html)\n- 二、开发环境搭建\n  - [2.1 【后端】环境安装&工具准备](https://www.quanxiaoha.com/column/10003.html)\n  - [2.2 【前端】开发环境&工具安装](https://www.quanxiaoha.com/column/10004.html)\n\n- 三、Spring Boot 后端工程搭建\n  - [3.1 搭建 Spring Boot 多模块工程](https://www.quanxiaoha.com/column/10005.html)\n  - [3.2 Spring Boot 多环境配置](https://www.quanxiaoha.com/column/10006.html)\n  - [3.3 配置 Lombok](https://www.quanxiaoha.com/column/10007.html)\n  - [3.4 Spring Boot 整合 Lockback 日志](https://www.quanxiaoha.com/column/10008.html)\n  - [3.5 Spring Boot 自定义注解，实现 API 请求日志切面](https://www.quanxiaoha.com/column/10009.html)\n  - [3.6 Spring Boot 通过 MDC 实现日志跟踪](https://www.quanxiaoha.com/column/10010.html)\n  - [3.7 Spring Boot 实现优雅的参数校验](https://www.quanxiaoha.com/column/10011.html)\n  - [3.8 Spring Boot 自定义响应工具类](https://www.quanxiaoha.com/column/10012.html)\n  - [3.9 Spring Boot 实现全局异常管理](https://www.quanxiaoha.com/column/10013.html)\n  - [3.10 全局异常处理器+参数校验（最佳实践）](https://www.quanxiaoha.com/column/10014.html)\n  - [3.11 整合 Knife4j：提升接口调试效率](https://www.quanxiaoha.com/column/10015.html)\n  - [3.12 自定义 Jackson 序列化、反序列化，支持 Java 8 日期新特性](https://www.quanxiaoha.com/column/10016.html)\n  - [3.13 小结](https://www.quanxiaoha.com/column/10017.html)\n\n- 四、使用 Vue 3 + Vite 4 搭建前端工程\n  - [4.1 Vue 3 环境安装& Weblog 项目搭建](https://www.quanxiaoha.com/column/10018.html)\n  - [4.2 安装 VSCode 开发工具](https://www.quanxiaoha.com/column/10019.html)\n  - [4.3 添加 vue-router 路由管理器](https://www.quanxiaoha.com/column/10020.html)\n  - [4.4 Vite 配置路径别名：更方便的引用文件](https://www.quanxiaoha.com/column/10021.html)\n  - [4.5 整合 Tailwind CSS](https://www.quanxiaoha.com/column/10022.html)\n  - [4.6 整合 Tailwind CSS 组件库：Flowbite](https://www.quanxiaoha.com/column/10023.html)\n  - [4.7 整合饿了么 Element Plus 组件库](https://www.quanxiaoha.com/column/10024.html)\n\n- 五、登录模块开发\n  - [5.1 登录页设计：支持响应式布局](https://www.quanxiaoha.com/column/10025.html)\n  - [5.2 登录页加点盐：通过 Animate.css 添加动画](https://www.quanxiaoha.com/column/10026.html)\n  - [5.3 整合 Mybatis Plus](https://www.quanxiaoha.com/column/10027.html)\n  - [5.4 p6spy 组件打印完整的 SQL 语句、执行耗时](https://www.quanxiaoha.com/column/10028.html)\n  - [5.5 整合 Spring Security](https://www.quanxiaoha.com/column/10029.html)\n  - [5.6 Spring Security 整合 JWT ：实现身份认证](https://www.quanxiaoha.com/column/10030.html)\n  - [5.7 Spring Security 整合 JWT ：实现接口鉴权](https://www.quanxiaoha.com/column/10031.html)\n  - [5.8 Vue 整合 Axios 实现登录功能](https://www.quanxiaoha.com/column/10032.html)\n  - [5.9 登录页表单验证](https://www.quanxiaoha.com/column/10033.html)\n  - [5.10 登录消息提示、回车键监听、按钮加载 Loading](https://www.quanxiaoha.com/column/10034.html)\n  - [5.11 存储 Token 到 Cookie 中](https://www.quanxiaoha.com/column/10035.html)\n  - [5.12 Axios 添加请求拦截器、响应拦截器](https://www.quanxiaoha.com/column/10036.html)\n  - [5.13 全局路由拦截：实现页面标题动态设置、后台路由跳转的登录判断](https://www.quanxiaoha.com/column/10037.html)\n  - [5.14 实现页面顶部加载 Loading 效果](https://www.quanxiaoha.com/column/10038.html)\n  - [5.15 重复登录问题优化、密码框可显示密码](https://www.quanxiaoha.com/column/10040.html)\n  - [5.16 角色鉴权：添加演示账号，仅支持查询操作](https://www.quanxiaoha.com/column/10089.html)\n  \n  \n  \n\n- 六、Element Plus 手搭 Admin 管理后台骨架\n  - [6.1 搭建管理后台基本布局](https://www.quanxiaoha.com/column/10039.html)\n  - [6.2 后台公共 Header 头：样式布局](https://www.quanxiaoha.com/column/10041.html)\n  - [6.3 后台公共左侧 Menu 菜单栏：样式布局](https://www.quanxiaoha.com/column/10042.html)\n  - [6.4 整合全局状态管理库 Pinia](https://www.quanxiaoha.com/column/10043.html)\n  - [6.5 左边菜单栏点击收缩、展开功能实现](https://www.quanxiaoha.com/column/10044.html)\n  - [6.6 支持全屏展示、页面点击刷新](https://www.quanxiaoha.com/column/10045.html)\n  - [6.7 标签导航栏组件实现：样式布局](https://www.quanxiaoha.com/column/10046.html)\n  - [6.8 标签导航栏组件实现：路由同步 (1)](https://www.quanxiaoha.com/column/10047.html)\n  - [6.9 标签导航栏组件实现：路由同步 (2)](https://www.quanxiaoha.com/column/10048.html)\n  - [6.10 标签导航栏组件实现：标签页关闭](https://www.quanxiaoha.com/column/10049.html)\n  - [6.11 标签导航栏组件实现：关闭其他、全部标签页](https://www.quanxiaoha.com/column/10050.html)\n  - [6.12 后台公共 Footer 页脚：样式布局](https://www.quanxiaoha.com/column/10051.html)\n  - [6.13 使用 KeepAlive 缓存组件，提高页面切换性能和响应速度](https://www.quanxiaoha.com/column/10052.html)\n  - [6.14 使用 Transition 组件添加全局过渡动画](https://www.quanxiaoha.com/column/10053.html)\n  - [6.15 修改用户密码接口开发](https://www.quanxiaoha.com/column/10054.html)\n  - [6.16 获取当前登录用户信息接口开发](https://www.quanxiaoha.com/column/10055.html)\n  - [6.17 Pinia 存储用户信息，动态显示登录用户名](https://www.quanxiaoha.com/column/10056.html)\n  - [6.18 使用 pinia-persist 插件实现 Pinia 数据持久化](https://www.quanxiaoha.com/column/10057.html)\n  - [6.19 用户修改密码、退出登录功能开发](https://www.quanxiaoha.com/column/10058.html)\n  - [6.20 小结](https://www.quanxiaoha.com/column/10059.html)\n\n  \n\n  \n- 七、管理后台：文章分类模块开发\n  - [7.1 分类模块接口分析](https://www.quanxiaoha.com/column/10060.html)\n  - [7.2 文章分类：新增接口开发](https://www.quanxiaoha.com/column/10061.html)\n  - [7.3 文章分类：分页接口开发](https://www.quanxiaoha.com/column/10062.html)\n  - [7.4 文章分类：删除接口开发](https://www.quanxiaoha.com/column/10063.html)\n  - [7.5 文章发布：分类 Select 下拉列表接口开发](https://www.quanxiaoha.com/column/10064.html)\n  - [7.6 后台分类管理页面：样式布局](https://www.quanxiaoha.com/column/10065.html)\n  - [7.7 Config Provider 全局配置: 实现组件中文化](https://www.quanxiaoha.com/column/10066.html)\n  - [7.8 文章分类：分页列表数据动态渲染](https://www.quanxiaoha.com/column/10067.html)\n  - [7.9 文章分类：新增功能开发](https://www.quanxiaoha.com/column/10068.html)\n  - [7.10 文章分类：删除功能开发](https://www.quanxiaoha.com/column/10069.html)\n  - [7.11 通用表单对话框组件封装](https://www.quanxiaoha.com/column/10070.html)\n  - [7.12 添加 Table 组件加载 Loading 、表单对话框提交按钮 Loading 动画](https://www.quanxiaoha.com/column/10071.html)\n  \n\n\n- 八、管理后台：标签模块开发\n  - [8.1 标签模块接口分析【视频讲解】](https://www.quanxiaoha.com/column/10072.html)\n  - [8.2 标签管理：新增标签接口开发【视频讲解】](https://www.quanxiaoha.com/column/10073.html)\n  - [8.3 标签管理：标签分页接口开发【视频讲解】](https://www.quanxiaoha.com/column/10074.html)\n  - [8.4 标签管理：删除标签接口开发【视频讲解】](https://www.quanxiaoha.com/column/10075.html)\n  - [8.5 标签关键词模糊查询 select 列表接口开发【视频讲解】](https://www.quanxiaoha.com/column/10076.html)\n  - [8.6 标签管理页面开发：分页列表【视频讲解】](https://www.quanxiaoha.com/column/10077.html)\n  - [8.7 标签管理页面开发：新增&删除标签功能【视频讲解】](https://www.quanxiaoha.com/column/10078.html)\n  \n  \n  \n- 九、管理后台：博客设置模块开发\n  - [9.1 博客设置模块功能分析、表设计](https://www.quanxiaoha.com/column/10079.html)\n  - [9.2 Docker 本地安装 Minio 对象存储](https://www.quanxiaoha.com/column/10080.html)\n  - [9.3 文件上传接口开发](https://www.quanxiaoha.com/column/10081.html)\n  - [9.4 博客设置: 更新接口开发](https://www.quanxiaoha.com/column/10082.html)\n  - [9.5 整合 Mapstruct : 简化属性映射](https://www.quanxiaoha.com/column/10083.html)\n  - [9.6 博客设置：获取详情接口开发](https://www.quanxiaoha.com/column/10084.html)\n  - [9.7 博客设置页面：样式布局](https://www.quanxiaoha.com/column/10085.html)\n  - [9.8 管理后台：滚动样式优化](https://www.quanxiaoha.com/column/10086.html)\n  - [9.9 博客设置页：数据渲染、图片上传](https://www.quanxiaoha.com/column/10087.html)\n  - [9.10 博客设置页：更新设置](https://www.quanxiaoha.com/column/10088.html)\n  \n\n\n\n- 十、管理后台：文章模块开发\n  - [10.1 文章管理模块功能分析、表设计](https://www.quanxiaoha.com/column/10090.html)\n  - [10.2 文章管理：文章发布接口开发（1）](https://www.quanxiaoha.com/column/10091.html)\n  - [10.3 文章管理：文章发布接口开发（2）—— SQL 注入器实现批量插入](https://www.quanxiaoha.com/column/10092.html)\n  - [10.4 文章管理：文章删除接口开发](https://www.quanxiaoha.com/column/10093.html)\n  - [10.5 文章管理：分页接口开发](https://www.quanxiaoha.com/column/10094.html)\n  - [10.6 文章管理：获取文章详情接口开发](https://www.quanxiaoha.com/column/10095.html)\n  - [10.7 文章管理：文章更新接口开发](https://www.quanxiaoha.com/column/10096.html)\n  - [10.8 文章管理：分页列表开发](https://www.quanxiaoha.com/column/10097.html)\n  - [10.9 文章管理页：删除文章开发](https://www.quanxiaoha.com/column/10098.html)\n  - [10.10 文章管理页：写文章对话框样式布局](https://www.quanxiaoha.com/column/10099.html)\n  - [10.11 文章管理页：文章发布功能开发](https://www.quanxiaoha.com/column/10100.html)\n  - [10.12 文章管理：获取所有标签 Select 列表接口开发](https://www.quanxiaoha.com/column/10101.html)\n  - [10.13 文章管理页：文章编辑功能开发](https://www.quanxiaoha.com/column/10102.html)\n  - [10.14 Bug 修复：分类、标签删除接口添加是否关联文章校验; 前端 token 过期问题 fixed](https://www.quanxiaoha.com/column/10103.html)\n  \n  \n  \n  \n  \n\n- 十一、博客前台：首页开发\n   - [11.1 前台首页、归档页接口分析](https://www.quanxiaoha.com/column/10104.html)\n   - [11.2 前台首页：文章分页接口开发](https://www.quanxiaoha.com/column/10105.html)\n   - [11.3 公共侧边栏：获取分类、标签列表接口开发](https://www.quanxiaoha.com/column/10106.html)\n   - [11.4 公共部分：获取博客设置信息接口开发](https://www.quanxiaoha.com/column/10107.html)\n   - [11.5 前台 Header 头组件封装](https://www.quanxiaoha.com/column/10108.html)   \n   - [11.6 首页样式布局设计（1）](https://www.quanxiaoha.com/column/10109.html)\n   - [11.7 首页样式布局设计（2） —— 侧边栏博主信息卡片](https://www.quanxiaoha.com/column/10110.html)\n   - [11.8 首页样式布局设计（3） —— 侧边栏分类、标签卡片](https://www.quanxiaoha.com/column/10111.html)\n   - [11.9 首页样式布局设计（4） —— Footer 组件封装](https://www.quanxiaoha.com/column/10112.html)\n   - [11.10 首页文章分页数据渲染](https://www.quanxiaoha.com/column/10113.html)\n   - [11.11 公共右边栏：博主信息卡片组件封装](https://www.quanxiaoha.com/column/10114.html)\n   - [11.12 公共右边栏：分类、标签卡片组件封装](https://www.quanxiaoha.com/column/10115.html)\n   - [11.13 公共 Header 头：跳转后台、退出登录功能开发](https://www.quanxiaoha.com/column/10116.html)\n   \n   \n\n- 十二、博客前台：归档列表页、分类列表页、标签列表页开发\n   - [12.1 归档页、分类列表页接口分析](https://www.quanxiaoha.com/column/10117.html)\n   - [12.2 文章归档分页接口开发](https://www.quanxiaoha.com/column/10118.html)\n   - [12.3 前台归档页：样式布局设计](https://www.quanxiaoha.com/column/10119.html)\n   - [12.4 前台归档页：分页列表功能开发](https://www.quanxiaoha.com/column/10120.html)\n   - [12.5 前台分类页开发](https://www.quanxiaoha.com/column/10121.html)\n   - [12.6 获取某个分类下的文章列表——分页接口开发](https://www.quanxiaoha.com/column/10122.html)\n   - [12.7 前台分类-文章列表页: 样式布局开发](https://www.quanxiaoha.com/column/10123.html)\n   - [12.8 分类-文章列表页开发](https://www.quanxiaoha.com/column/10124.html)\n   - [12.9 前台标签列表页：样式布局&功能开发](https://www.quanxiaoha.com/column/10125.html)\n   - [12.10 获取某个标签下的文章列表——分页接口开发](https://www.quanxiaoha.com/column/10130.html)\n   - [12.11 标签-文章列表页开发](https://www.quanxiaoha.com/column/10131.html)\n\n\n- 十三、博客前台：文章详情页开发\n   - [13.1 文章详情页接口分析](https://www.quanxiaoha.com/column/10126.html)\n   - [13.2 后端封装 Markdown 装换工具类](https://www.quanxiaoha.com/column/10127.html)\n   - [13.3 获取文章详情接口开发](https://www.quanxiaoha.com/column/10128.html)\n   - [13.4 文章详情页：样式布局设计](https://www.quanxiaoha.com/column/10129.html)\n\n   - *努力爆肝中，每天更新两小节, 按目前的更新速度，1.0 版本差不多还剩1个半月更新完毕...*\n- 十四、管理后台：仪表盘模块开发\n- 十五、项目部署上线\n  - 云服务器选购\n  - 相关环境安装（JDK、Docker、Nginx、Mysql）\n  - Nginx 配合 Spring Boot 部署\n  - 部署前端项目以及通过 IP 访问\n  - 域名选购\n  - 网站备案\n  - 域名映射，项目正式上线\n\n\n\n## 👨🏻‍💻 适用人群\n\n- **在校学生**，有一定基础，想做毕业设计，或者为找工作准备，需要实战项目加分；\n\n  > 💡 TIP: 小白也没关系，小哈将会告诉你学习路线是啥，哪里有免费的高质量学习视频可以白嫖，学完这些技术栈后再来做实战项目，或者学一点基础边实战边学习都可以。\n\n- **已经参与工作，对前后端分离感兴趣**，想学习 Vue 3 前端，对独立上线自己网站感兴趣的童鞋；\n- **想独立接私活**，需要同时会后端、前端技术栈的童鞋；\n\n## ✊ 如何加入？\n\n小哈已经将本站的专栏模块接入了知识星球，想要查看专栏内容，需要订阅我星球后，*微信扫码授权登录后即可解锁所有内容*。因为目前也是刚开始运营，所以价格不会太高，星球官方定价最低必须是 50 元。小哈最终定价为 <font class=\"text-xl\" style=\'color: red\'><b>限时 35 元（附 15 元的优惠券，记得扫码领取下方优惠券加入哟）</b></font>，后续随着内容慢慢的更新迭代，会慢慢涨上去，所以早加入更具性价比哟~ \n\n<font class=\"text-xl\" style=\'color: red\'><b>星球支持 3 天无理由退费</b></font>，感兴趣的小伙伴*可先加入，看看内容质量如何，不合适直接退款就行，觉得确实内容很干货，就留下来学习，无套路!*\n\n<div class=\"flex items-center justify-center text-lg text-red-500 font-bold mb-2\">扫描下方二维码加入, 星球支持 3 天无理由退款，可以先进去看看合不合适👇👇</div>\n\n![\"领取优惠券加入，更划算\"](https://img.quanxiaoha.com/quanxiaoha/169355760680941 \"领取优惠券加入，更划算\")\n\n<div class=\"flex items-center justify-center text-lg text-red-500 font-bold\">扫描上方二维码加入, 星球支持 3 天无理由退款，可以先进去看看合不合适👆👆</div>\n\n\n\n\n## ❓ 关于答疑\n\n小伙伴们如果在跟着专栏学习，手敲项目的过程中遇到问题，碰到无法解决的问题，**可在小哈的知识星球内部提问**，我会统一来解答, 如果星球说不清楚的，就加私人微信，打包发项目，亲自给你看哪一步有问题，保证跟上项目进度，不落下任何一个小伙伴，大家一起冲冲冲~\n\n## 😃 加微信咨询\n\n对专栏感兴趣的小伙伴，也可以加小哈私人微信来咨询，扫描下方二维码即可，记得备注【*咨询*】哟：\n\n![扫描二维码，添加小哈私人微信](https://img.quanxiaoha.com/quanxiaoha/169536889316499 \"扫描二维码，添加小哈私人微信\")\n');
INSERT INTO `t_article_content` VALUES (8, 8, '如果引入新的依赖，该依赖的版本号和名称都没问题，**Reload之后报错**，即有可能是其他依赖版本缺失问题，在maven中**<u>任何一个依赖版本有问题</u>**都会导致整体的***构建错误***');
INSERT INTO `t_article_content` VALUES (9, 9, '\n首先，定义一个注解ApiOperationLog\n```language\n@Retention(RetentionPolicy.RUNTIME)\n@Target(ElementType.METHOD)\n@Documented\npublic @interface ApiOperationLog {\n\n    String description() default \"\";\n}\n```\n\n```language\n//--------------对横切关注点进行封装的类------------------------------------\n@Aspect\n@Component\n@Slf4j\npublic class ApiOperationLogAspect {\n//------------切点，通知功能被应用的范围，下面这个意思是对与所有加ApiOperationLog的注解的方法------------------------------\n    @Pointcut(\"@annotation(com.example.weblog.module.common.aspect.ApiOperationLog)\")\n    public void apiOperationLog(){}\n//-----------通知（调用方法前、后、返回后、抛出异常后、环绕）-------------------------------------------------------------------\n    @Around(\"apiOperationLog()\")\n    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {\n        long startTime = System.currentTimeMillis();\n\n        try {\n            MDC.put(\"traceId\",\n                    UUID.randomUUID().toString());\n            //获取被请求的类和方法\n            String className = joinPoint.getTarget().getClass().getSimpleName();\n            String methodName = joinPoint.getSignature().getName();\n\n            //请求入参\n            Object[] args = joinPoint.getArgs();\n            //入参转Json字符串\n            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(\", \"));\n            //功能描述信息\n            String description = getApiOperationLogDescription(joinPoint);\n            //打印请求相关参数\n            log.info(\"===== 请求开始：[{}], 入参：{},请求类：{},请求方法：{}=====================\", description, argsJsonStr, className, methodName);\n            //执行切点方法。joinPoint,连接点通知应用的时机\n            Object result = joinPoint.proceed();\n            //执行耗时\n            long executionTime = System.currentTimeMillis() - startTime;\n            //打印出参等相关信息\n            log.info(\"=====请求结束:[{}],耗时：{}ms,出参：{}============\",description, executionTime, JasonUtil.toJsonString(result));\n            return result;\n        } finally {\n            MDC.clear();\n\n        }\n    }\n    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint){\n        //1、从ProceedingJoinPoint 获取MethodSignature\n        MethodSignature signature = (MethodSignature) joinPoint.getSignature();\n\n        //2、使用methodSignature获取当前被注解的Method\n        Method method = signature.getMethod();\n\n        //3、从Method中提取LogExecution注解\n        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);\n\n        //4、从LogExecution 注解中获取description属性\n        return  apiOperationLog.description();\n\n\n    }\n    private Function<Object, String> toJsonStr(){\n        return arg -> JasonUtil.toJsonString(arg);\n    }\n}\n\n```\n\n');
INSERT INTO `t_article_content` VALUES (10, 10, '\n```java\n@Slf4j\n@Service\npublic class UserActivityRankServiceImpl implements UserActivityRankService {\n    private static final String ACTIVITY_SCORE_KEY = \"activity_rank_\";\n\n    @Autowired\n    private UserService userService;\n\n    /**\n     * 当天活跃度排行榜\n     *\n     * @return 当天排行榜key\n     */\n    private String todayRankKey() {\n        return ACTIVITY_SCORE_KEY + DateUtil.format(DateTimeFormatter.ofPattern(\"yyyyMMdd\"), System.currentTimeMillis());\n    }\n\n    /**\n     * 本月排行榜\n     *\n     * @return 月度排行榜key\n     */\n    private String monthRankKey() {\n        return ACTIVITY_SCORE_KEY + DateUtil.format(DateTimeFormatter.ofPattern(\"yyyyMM\"), System.currentTimeMillis());\n    }\n\n    /**\n     * 添加活跃分\n     *\n     * @param userId        用于更新活跃积分的用户\n     * @param activityScore 触发活跃积分的时间类型\n     */\n    @Override\n    public void addActivityScore(Long userId, ActivityScoreBo activityScore) {\n        if (userId == null) {\n            return;\n        }\n\n        // 1. 计算活跃度(正为加活跃,负为减活跃)\n        String field;\n        int score = 0;\n        if (activityScore.getPath() != null) {\n            field = \"path_\" + activityScore.getPath();\n            score = 1;\n        } else if (activityScore.getArticleId() != null) {\n            field = activityScore.getArticleId() + \"_\";\n            if (activityScore.getPraise() != null) {\n                field += \"praise\";\n                score = BooleanUtils.isTrue(activityScore.getPraise()) ? 2 : -2;\n            } else if (activityScore.getCollect() != null) {\n                field += \"collect\";\n                score = BooleanUtils.isTrue(activityScore.getCollect()) ? 2 : -2;\n            } else if (activityScore.getRate() != null) {\n                // 评论回复\n                field += \"rate\";\n                score = BooleanUtils.isTrue(activityScore.getRate()) ? 3 : -3;\n            } else if (BooleanUtils.isTrue(activityScore.getPublishArticle())) {\n                // 发布文章\n                field += \"publish\";\n                score += 10;\n            }\n        } else if (activityScore.getFollowedUserId() != null) {\n            // 关注添加积分\n            field = activityScore.getFollowedUserId() + \"_follow\";\n            score = BooleanUtils.isTrue(activityScore.getFollow()) ? 2 : -2;\n        } else {\n            return;\n        }\n\n        final String todayRankKey = todayRankKey();\n        final String monthRankKey = monthRankKey();\n        // 2. 幂等：判断之前是否有更新过相关的活跃度信息\n        final String userActionKey = ACTIVITY_SCORE_KEY + userId + DateUtil.format(DateTimeFormatter.ofPattern(\"yyyyMMdd\"), System.currentTimeMillis());\n        Integer ans = RedisClient.hGet(userActionKey, field, Integer.class);\n        if (ans == null) {\n            // 2.1 之前没有加分记录，执行具体的加分\n            if (score > 0) {\n                // 记录加分记录\n                RedisClient.hSet(userActionKey, field, score);\n                // 个人用户的操作记录，保存一个月的有效期，方便用户查询自己最近31天的活跃情况\n                RedisClient.expire(userActionKey, 31 * DateUtil.ONE_DAY_SECONDS);\n\n                // 更新当天和当月的活跃度排行榜\n                Double newAns = RedisClient.zIncrBy(todayRankKey, String.valueOf(userId), score);\n                RedisClient.zIncrBy(monthRankKey, String.valueOf(userId), score);\n                if (log.isDebugEnabled()) {\n                    log.info(\"活跃度更新加分! key#field = {}#{}, add = {}, newScore = {}\", todayRankKey, userId, score, newAns);\n                }\n                if (newAns <= score) {\n                    // 由于上面只实现了日/月活跃度的增加，但是没有设置对应的有效期；为了避免持久保存导致redis占用较高；因此这里设定了缓存的有效期\n                    // 日活跃榜单，保存31天；月活跃榜单，保存1年\n                    // 为什么是 newAns <= score 才设置有效期呢？\n                    // 因为 newAns 是用户当天的活跃度，如果发现和需要增加的活跃度 scopre 相等，则表明是今天的首次添加记录，此时设置有效期就比较符合预期了\n                    // 但是请注意，下面的实现有两个缺陷：\n                    //  1. 对于月的有效期，就变成了本月，每天的首次增加活跃度时，都会重新刷一下它的有效期，这样就和预期中的首次添加缓存时，设置有效期不符\n                    //  2. 若先增加活跃度1，再减少活跃度1，然后再加活跃度1，同样会导致重新算了有效期\n                    // 严谨一些的写法，应该是 先判断 key 的 ttl， 对于没有设置的才进行设置有效期，如下\n                    Long ttl = RedisClient.ttl(todayRankKey);\n                    if (!NumUtil.upZero(ttl)) {\n                        RedisClient.expire(todayRankKey, 31 * DateUtil.ONE_DAY_SECONDS);\n                    }\n                    ttl = RedisClient.ttl(monthRankKey);\n                    if (!NumUtil.upZero(ttl)) {\n                        RedisClient.expire(monthRankKey, 12 * DateUtil.ONE_MONTH_SECONDS);\n                    }\n                }\n            }\n        } else if (ans > 0) {\n            // 2.2 之前已经加过分，因此这次减分可以执行\n            if (score < 0) {\n                // 移除用户的活跃执行记录 --> 即移除用来做防重复添加活跃度的幂等键\n                Boolean oldHave = RedisClient.hDel(userActionKey, field);\n                if (BooleanUtils.isTrue(oldHave)) {\n                    Double newAns = RedisClient.zIncrBy(todayRankKey, String.valueOf(userId), score);\n                    RedisClient.zIncrBy(monthRankKey, String.valueOf(userId), score);\n                    if (log.isDebugEnabled()) {\n                        log.info(\"活跃度更新减分! key#field = {}#{}, add = {}, newScore = {}\", todayRankKey, userId, score, newAns);\n                    }\n                }\n            }\n        }\n    }\n\n\n```\n');

-- ----------------------------
-- Table structure for t_article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_rel`;
CREATE TABLE `t_article_tag_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) UNSIGNED NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE,
  INDEX `idx_tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章对应标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_tag_rel
-- ----------------------------
INSERT INTO `t_article_tag_rel` VALUES (4, 6, 11);
INSERT INTO `t_article_tag_rel` VALUES (22, 8, 11);
INSERT INTO `t_article_tag_rel` VALUES (23, 9, 12);
INSERT INTO `t_article_tag_rel` VALUES (24, 7, 12);
INSERT INTO `t_article_tag_rel` VALUES (25, 7, 11);
INSERT INTO `t_article_tag_rel` VALUES (26, 10, 10);

-- ----------------------------
-- Table structure for t_blog_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_settings`;
CREATE TABLE `t_blog_settings`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '介绍语',
  `avatar` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'GitHub 主页访问地址',
  `csdn_homepage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'CSDN 主页访问地址',
  `gitee_homepage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Gitee 主页访问地址',
  `zhihu_homepage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '知乎主页访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_settings
-- ----------------------------
INSERT INTO `t_blog_settings` VALUES (1, 'http://192.168.134.128:9000/weblog/16bdbe86680d4e7dbc754f30594b8c9a.png', '中医体质测评', '小哈学Java', '哈喽', 'http://192.168.134.128:9000/weblog/dce422708336423dac05821c4d0dc624.jpg', '', '', '', '');

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体质描述',
  `characteristics` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '形体特征',
  `common_symptoms` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '常见表现',
  `psychological_traits` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '心理特征',
  `diet_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '饮食建议',
  `lifestyle_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '生活建议',
  `exercise_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '运动建议',
  `acupoint_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '穴位建议',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (2, '阴虚质', '2025-08-05 20:18:28', '2025-08-05 20:18:28', 0, '阴液亏少，口燥咽干、手足心热等虚热表现为主要特征', '体形偏瘦', '手足心热，口燥咽干，鼻微干，喜冷饮', '11', NULL, NULL, NULL, NULL);
INSERT INTO `t_category` VALUES (3, '阳虚质', '2025-08-05 20:18:52', '2025-08-05 20:18:52', 0, '阳气不足，畏寒怕冷、手足不温等虚寒表现为主要特征', '肌肉松软不实', '平素畏冷，手足不温，喜热饮食，精神不振', '11', NULL, NULL, NULL, NULL);
INSERT INTO `t_category` VALUES (4, '痰湿质', '2025-08-08 21:51:12', '2025-08-08 21:51:12', 0, '痰湿凝聚，形体肥胖、腹部肥满、口黏苔腻', '体形肥胖，腹部肥满松软', '面部皮肤油脂较多，多汗且黏，胸闷，痰多', '11', NULL, NULL, NULL, NULL);
INSERT INTO `t_category` VALUES (5, '气郁质', '2025-08-08 21:51:31', '2025-08-08 21:51:31', 0, '气机郁滞，神情抑郁、忧虑脆弱', '形体瘦者为多', '神情抑郁，情感脆弱，烦闷不乐', '11', NULL, NULL, NULL, NULL);
INSERT INTO `t_category` VALUES (6, '血瘀质', '2025-12-05 20:53:07', '2025-12-05 20:53:07', 0, '血行不畅，以肤色晦黯、舌质紫黯', '胖瘦均见', '肤色晦黯，色素沉着，容易出现瘀斑', '11', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_statistics_article_pv
-- ----------------------------
DROP TABLE IF EXISTS `t_statistics_article_pv`;
CREATE TABLE `t_statistics_article_pv`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pv_date` date NOT NULL COMMENT '被统计的日期',
  `pv_count` bigint(20) UNSIGNED NOT NULL COMMENT 'pv访问量',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_pv_date`(`pv_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '统计表 - 文章 PV (访问量)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_statistics_article_pv
-- ----------------------------
INSERT INTO `t_statistics_article_pv` VALUES (1, '2025-09-14', 2, '2025-09-13 23:00:00', '2025-09-13 23:00:00');
INSERT INTO `t_statistics_article_pv` VALUES (2, '2025-10-14', 7, '2025-10-13 23:00:00', '2025-10-13 23:00:00');
INSERT INTO `t_statistics_article_pv` VALUES (3, '2025-10-15', 0, '2025-10-14 23:17:02', '2025-10-14 23:17:02');
INSERT INTO `t_statistics_article_pv` VALUES (4, '2025-12-06', 2, '2025-12-05 23:00:00', '2025-12-05 23:00:00');
INSERT INTO `t_statistics_article_pv` VALUES (5, '2025-12-07', 0, '2025-12-06 23:00:00', '2025-12-06 23:00:00');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (7, '标签2', '2025-08-14 15:30:48', '2025-08-14 15:30:48', 0);
INSERT INTO `t_tag` VALUES (8, '标签3', '2025-08-14 15:30:48', '2025-08-14 15:30:48', 0);
INSERT INTO `t_tag` VALUES (9, '标签5', '2025-08-14 16:56:58', '2025-08-14 16:56:58', 0);
INSERT INTO `t_tag` VALUES (10, '标签7', '2025-08-14 16:57:10', '2025-08-14 16:57:10', 0);
INSERT INTO `t_tag` VALUES (11, '标签1', '2025-08-17 23:31:03', '2025-08-17 23:31:03', 0);
INSERT INTO `t_tag` VALUES (12, '标签10', '2025-08-17 23:31:03', '2025-08-17 23:31:03', 0);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (4, 'admin', '$2a$10$u/58dNblPRAu0f0xWPmSnems/4i2WxXdz9LlWPhhj9X63bZiPznVy', '2025-07-23 14:20:14', '2025-08-04 14:03:41', 0);
INSERT INTO `t_user` VALUES (5, 'test', '$2a$10$z8tNfmRxyBEj4jUQB/aCKOpEPsPuSZllEmmU1tTGc3bFv8WiLmvGe', '2025-07-27 14:36:20', '2025-08-02 21:04:30', 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `role` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 'admin', 'ROLE_ADMIN', '2025-07-27 14:42:00');
INSERT INTO `t_user_role` VALUES (2, 'test', 'ROLE_VISITOR', '2025-07-27 14:42:30');

SET FOREIGN_KEY_CHECKS = 1;
