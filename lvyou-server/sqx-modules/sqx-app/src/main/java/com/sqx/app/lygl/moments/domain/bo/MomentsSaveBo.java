package com.sqx.app.lygl.moments.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class MomentsSaveBo {
    /**
     * 用户id
     */
    @Schema(description = "用户唯一标识符", example = "12345")
    private Long userId;

    /**
     * 标题
     */
    @Schema(description = "标题信息", example = "美丽的风景")
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容详情", example = "这里是一些描述性的文字。")
    private String content;

    /**
     * 图片 逗号分隔
     */
    @Schema(description = "图片链接，多个链接使用逗号分隔", example = "http://example.com/image1.jpg,http://example.com/image2.jpg")
    private String img;

    /**
     * 视频
     */
    @Schema(description = "视频")
    private String videoUrl;

    /**
     * 位置
     */
    @Schema(description = "地点的位置描述", example = "上海市浦东新区世纪大道")
    private String location;

    /**
     * 景点id
     */
    @Schema(description = "景点的唯一标识符", example = "67890")
    private Long scenicSpotId;

    /**
     * 浏览次数
     */
    @Schema(description = "浏览次数统计", example = "45")
    private Integer views;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "纬度")
    private Double longitude;

    @Schema(description = "经度")
    private Double latitude;

    @Schema(description = "商户id")
    private Long shopId;
}
