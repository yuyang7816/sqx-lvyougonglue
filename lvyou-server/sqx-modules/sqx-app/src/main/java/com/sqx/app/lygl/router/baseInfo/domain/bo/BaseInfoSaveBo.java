package com.sqx.app.lygl.router.baseInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class BaseInfoSaveBo {

    /**
     * 路线标题
     */
    @Schema(description = "路线的标题", example = "环湖自驾游")
    private String title;

    /**
     * 封面图
     */
    @Schema(description = "封面图片的URL", example = "http://example.com/image.jpg")
    private String imgUrl;

    /**
     * 发布者id
     */
    @Schema(description = "发布者的唯一标识符", example = "987654")
    private Long userId;

    /**
     * 描述
     */
    @Schema(description = "对路线或活动的详细描述", example = "享受自然美景，体验乡村生活。")
    private String remark;

    /**
     * 行程天数
     */
    @Schema(description = "行程预计需要的天数", example = "3天2夜")
    private String days;

    /**
     * 行驶公里数
     */
    @Schema(description = "整个行程预计行驶的公里数", example = "500")
    private Integer kilometres;

    /**
     * 最佳时间
     */
    @Schema(description = "进行此行程的最佳时间段", example = "春季(3月-5月)")
    private String times;

    private String tags;
}
