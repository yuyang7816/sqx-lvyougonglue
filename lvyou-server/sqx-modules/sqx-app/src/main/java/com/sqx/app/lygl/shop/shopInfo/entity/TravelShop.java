package com.sqx.app.lygl.shop.shopInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商户信息
 * @TableName travel_shop
 */
@TableName(value ="travel_shop")
@Data
public class TravelShop implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户类型id
     */
    @TableField(value = "type_id")
    private Long typeId;

    /**
     * 商户名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 评分
     */
    @TableField(value = "grade")
    private BigDecimal grade;

    /**
     * 开放时间
     */
    @TableField(value = "open_time")
    private String openTime;

    /**
     * 省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 纬度
     */
    @TableField(value = "lat")
    private BigDecimal lat;

    /**
     * 经度
     */
    @TableField(value = "lng")
    private BigDecimal lng;

    /**
     * 标签, 逗号分隔
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 收藏数
     */
    @TableField(value = "collect_num")
    private Integer collectNum;

    /**
     * 图片，都好分隔
     */
    @TableField(value = "img")
    private String img;

    /**
     * 联系方式
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 联系人姓名
     */
    @TableField(value = "contact_name")
    private String contactName;

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