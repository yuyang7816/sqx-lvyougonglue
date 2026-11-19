package com.sqx.app.lygl.concert.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 演唱会
 * @TableName travel_concert
 */
@TableName(value ="travel_concert")
@Data
public class TravelConcert implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 封面图
     */
    @TableField(value = "img")
    private String img;

    /**
     * 详情图
     */
    private String pic;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 演出时间
     */
    @TableField(value = "concert_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime concertTime;

    /**
     * 演出地点
     */
    @TableField(value = "location")
    private String location;

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

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}