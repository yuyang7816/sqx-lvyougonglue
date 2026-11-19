package com.sqx.app.lygl.scenicSpot.info.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class ScenicSpotUpdateBo {

    private Long id;

    /**
     * 景点名称
     */
    @Schema(description = "景点的名称")
    private String name;

    /**
     * 景点图片，以逗号分隔
     */
    @Schema(description = "与景点相关的图片链接，多个链接之间用逗号分隔")
    private String img;

    /**
     * 开放时间
     */
    @Schema(description = "景点对外开放的时间段")
    private String openHours;

    /**
     * 景点位置
     */
    @Schema(description = "景点所在的地理位置描述")
    private String location;

    /**
     * 门票价格
     */
    @Schema(description = "参观景点所需的门票价格")
    private BigDecimal ticketPrice;

    /**
     * 简介
     */
    @Schema(description = "对景点的基本介绍和相关信息")
    private String intro;

    /**
     * 交通攻略
     */
    @Schema(description = "前往景点的交通指南和建议")
    private String traffic;

    /**
     * 评分
     */
    @Schema(description = "评分")
    private Integer rate;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String tags;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;
}
