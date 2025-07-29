package com.petlife.platform.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 用户动态信息传输对象
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户动态信息")
public class UserPostDto {
    
    @ApiModelProperty("动态ID")
    private Long postId;
    
    @ApiModelProperty("发布用户ID")
    private Long userId;
    
    @ApiModelProperty("发布用户头像")
    private String userAvatar;
    
    @ApiModelProperty("发布用户昵称")
    private String userNickName;
    
    @ApiModelProperty("动态文字内容")
    private String content;
    
    @ApiModelProperty("媒体文件URLs")
    private List<String> mediaUrls;
    
    @ApiModelProperty("媒体类型（1=图文，2=视频，3=纯文字）")
    private Integer mediaType;
    
    @ApiModelProperty("媒体类型显示文本")
    private String mediaTypeText;
    
    @ApiModelProperty("发布地点")
    private String location;
    
    @ApiModelProperty("IP属地")
    private String ipLocation;
    
    @ApiModelProperty("可见范围（1=所有人，2=仅粉丝，3=仅自己）")
    private Integer visibility;
    
    @ApiModelProperty("可见范围显示文本")
    private String visibilityText;
    
    @ApiModelProperty("点赞数")
    private Integer likeCount;
    
    @ApiModelProperty("评论数")
    private Integer commentCount;
    
    @ApiModelProperty("分享数")
    private Integer shareCount;
    
    @ApiModelProperty("浏览数")
    private Integer viewCount;
    
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty("发布时间显示文本")
    private String createTimeText;
    
    @ApiModelProperty("当前用户是否已点赞")
    private Boolean isLiked;
    
    @ApiModelProperty("当前用户是否已收藏")
    private Boolean isFavorited;
    
    @ApiModelProperty("是否为当前用户发布")
    private Boolean isOwner;
    
    @ApiModelProperty("是否可以查看（基于可见范围和关注关系）")
    private Boolean canView;
    
    @ApiModelProperty("评论列表（可选，用于详情页）")
    private List<PostCommentDto> comments;
    
    /**
     * 动态评论信息内部类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("动态评论信息")
    public static class PostCommentDto {
        
        @ApiModelProperty("评论ID")
        private Long commentId;
        
        @ApiModelProperty("评论用户ID")
        private Long userId;
        
        @ApiModelProperty("评论用户头像")
        private String userAvatar;
        
        @ApiModelProperty("评论用户昵称")
        private String userNickName;
        
        @ApiModelProperty("评论内容")
        private String content;
        
        @ApiModelProperty("评论点赞数")
        private Integer likeCount;
        
        @ApiModelProperty("评论时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;
        
        @ApiModelProperty("评论时间显示文本")
        private String createTimeText;
        
        @ApiModelProperty("父评论ID")
        private Long parentId;
        
        @ApiModelProperty("回复的用户昵称")
        private String replyToUserName;
        
        @ApiModelProperty("当前用户是否已点赞此评论")
        private Boolean isLiked;
        
        @ApiModelProperty("是否为当前用户发布")
        private Boolean isOwner;
        
        /**
         * 格式化评论时间显示
         */
        public void formatCreateTime() {
            if (createTime == null) {
                this.createTimeText = "";
                return;
            }
            
            LocalDateTime now = LocalDateTime.now();
            long minutes = ChronoUnit.MINUTES.between(createTime, now);
            long hours = ChronoUnit.HOURS.between(createTime, now);
            long days = ChronoUnit.DAYS.between(createTime, now);
            
            if (minutes <= 60) {
                this.createTimeText = minutes + "分前";
            } else if (hours <= 24) {
                this.createTimeText = hours + "小时前";
            } else {
                this.createTimeText = createTime.toLocalDate().toString();
            }
        }
    }
    
    /**
     * 设置媒体类型显示文本
     */
    public void setMediaTypeText() {
        if (mediaType == null) {
            this.mediaTypeText = "未知";
            return;
        }
        
        switch (mediaType) {
            case 1:
                this.mediaTypeText = "图文";
                break;
            case 2:
                this.mediaTypeText = "视频";
                break;
            case 3:
                this.mediaTypeText = "文字";
                break;
            default:
                this.mediaTypeText = "未知";
                break;
        }
    }
    
    /**
     * 设置可见范围显示文本
     */
    public void setVisibilityText() {
        if (visibility == null) {
            this.visibilityText = "未知";
            return;
        }
        
        switch (visibility) {
            case 1:
                this.visibilityText = "所有人可见";
                break;
            case 2:
                this.visibilityText = "仅粉丝可见";
                break;
            case 3:
                this.visibilityText = "仅自己可见";
                break;
            default:
                this.visibilityText = "未知";
                break;
        }
    }
    
    /**
     * 格式化发布时间显示
     * 按需求规则：≤1小时显示分钟，≤24小时显示小时，>24小时显示日期
     */
    public void formatCreateTime() {
        if (createTime == null) {
            this.createTimeText = "";
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(createTime, now);
        long hours = ChronoUnit.HOURS.between(createTime, now);
        long days = ChronoUnit.DAYS.between(createTime, now);
        
        if (minutes <= 60) {
            this.createTimeText = minutes + "分前";
        } else if (hours <= 24) {
            this.createTimeText = hours + "小时前";
        } else {
            // 显示具体日期，格式：2024-06-25
            this.createTimeText = createTime.toLocalDate().toString();
        }
    }
    
    /**
     * 处理评论列表的时间格式化
     */
    public void processComments() {
        if (comments != null && !comments.isEmpty()) {
            for (PostCommentDto comment : comments) {
                comment.formatCreateTime();
            }
        }
    }
    
    /**
     * 检查内容是否需要折叠（超过5行）
     */
    public boolean isContentTooLong() {
        if (content == null || content.isEmpty()) {
            return false;
        }
        
        // 简单按换行符计算行数，实际应用中可能需要更复杂的计算
        String[] lines = content.split("\n");
        return lines.length > 5;
    }
    
    /**
     * 获取折叠后的内容（前5行）
     */
    public String getTruncatedContent() {
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        String[] lines = content.split("\n");
        if (lines.length <= 5) {
            return content;
        }
        
        StringBuilder truncated = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i > 0) {
                truncated.append("\n");
            }
            truncated.append(lines[i]);
        }
        truncated.append("...");
        
        return truncated.toString();
    }
}
