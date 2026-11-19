package com.sqx.app.lygl.comment.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户评论
 * @TableName travel_comment
 */
@TableName(value ="travel_comment")
@Data
public class TravelComment implements Serializable {
    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 关联数据id
     */
    @TableField(value = "data_id")
    private Long dataId;

    /**
     * 关联数据类型 1动态
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 评论图片，支持多个，逗号分隔
     */
    @TableField(value = "content_img")
    private String contentImg;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}