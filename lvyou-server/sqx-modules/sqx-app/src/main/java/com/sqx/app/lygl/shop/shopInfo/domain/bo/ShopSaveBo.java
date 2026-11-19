package com.sqx.app.lygl.shop.shopInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class ShopSaveBo {

    /**
     * 商户类型id
     */
    @Schema(description = "商户类型的唯一标识符")
    private Long typeId;

    /**
     * 商户名称
     */
    @Schema(description = "商户的名称")
    private String name;

    /**
     * 简介
     */
    @Schema(description = "商户的简介信息")
    private String intro;

    /**
     * 评分
     */
    @Schema(description = "商户的评分")
    private BigDecimal grade;

    /**
     * 开放时间
     */
    @Schema(description = "商户的开放时间")
    private String openTime;

    /**
     * 省
     */
    @Schema(description = "商户所在的省份")
    private String province;

    /**
     * 市
     */
    @Schema(description = "商户所在的城市")
    private String city;

    /**
     * 区
     */
    @Schema(description = "商户所在的区域")
    private String region;

    /**
     * 详细地址
     */
    @Schema(description = "商户的详细地址")
    private String address;

    /**
     * 纬度
     */
    @Schema(description = "商户位置的纬度坐标")
    private BigDecimal lat;

    /**
     * 经度
     */
    @Schema(description = "商户位置的经度坐标")
    private BigDecimal lng;

    /**
     * 标签, 逗号分隔
     */
    @Schema(description = "与商户相关的标签，多个标签使用逗号分隔")
    private String tags;

    /**
     * 图片，都好分隔
     */
    @Schema(description = "与商户相关的图片链接，多个链接使用逗号分隔")
    private String img;

    /**
     * 联系方式
     */
    @Schema(description = "联系人的电话号码")
    private String phone;

    /**
     * 联系人姓名
     */
    @Schema(description = "联系人的姓名")
    private String contactName;
}
