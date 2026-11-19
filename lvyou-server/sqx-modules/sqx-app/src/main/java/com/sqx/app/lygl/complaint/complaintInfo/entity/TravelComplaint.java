package com.sqx.app.lygl.complaint.complaintInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户投诉表
 * @TableName travel_complaint
 */
@TableName(value ="travel_complaint")
@Data
public class TravelComplaint implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 投诉用户
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 目标id
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * 1用户 2群聊
     */
    @TableField(value = "target_type")
    private Integer targetType;

    /**
     * 投诉原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 投诉说明
     */
    @TableField(value = "Instruction")
    private String instruction;

    /**
     * 图片 以逗号分隔
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 状态1等待处理 2封禁账号 3已驳回 4通过但不做处理
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 处理反馈
     */
    @TableField(value = "feedback")
    private String feedback;

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