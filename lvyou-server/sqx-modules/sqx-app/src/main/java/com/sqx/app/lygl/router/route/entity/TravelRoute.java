package com.sqx.app.lygl.router.route.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 行程路线
 * @TableName travel_route
 */
@TableName(value ="travel_route")
@Data
public class TravelRoute implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路线基本信息id
     */
    @TableField(value = "base_info_id")
    private Long baseInfoId;

    /**
     * 标题
     */
    private String title;

    /**
     * 天数（第几天）
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 介绍
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 景点id，多个，以逗号分隔
     */
    @TableField(value = "scenic_spot_id")
    private String scenicSpotId;

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