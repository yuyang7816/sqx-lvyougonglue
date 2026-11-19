package com.sqx.app.lygl.scenicSpot.info.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 景点信息
 * @TableName travel_scenic_spot
 */
@TableName(value ="travel_scenic_spot")
@Data
public class TravelScenicSpot implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 景点名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 景点图片，以逗号分隔
     */
    @TableField(value = "img")
    private String img;

    /**
     * 开放时间
     */
    @TableField(value = "open_hours")
    private String openHours;

    /**
     * 景点位置
     */
    @TableField(value = "location")
    private String location;

    /**
     * 门票价格
     */
    @TableField(value = "ticket_price")
    private BigDecimal ticketPrice;

    /**
     * 简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 交通攻略
     */
    @TableField(value = "traffic")
    private String traffic;

    /**
     * 评分
     */
    private Integer rate;

    /**
     * 标签
     */
    private String tags;

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
    private Long collectNum;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}