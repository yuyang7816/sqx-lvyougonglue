package com.sqx.app.lygl.router.baseInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 路线基本信息
 * @TableName travel_base_info
 */
@TableName(value ="travel_base_info")
@Data
public class TravelBaseInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路线标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 封面图
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 状态 1正常 2禁用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 发布者id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 描述
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 行程天数
     */
    @TableField(value = "days")
    private String days;

    /**
     * 行驶公里数
     */
    @TableField(value = "kilometres")
    private Integer kilometres;

    /**
     * 最佳时间
     */
    @TableField(value = "times")
    private String times;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 标签，逗号分割
     */
    private String tags;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}