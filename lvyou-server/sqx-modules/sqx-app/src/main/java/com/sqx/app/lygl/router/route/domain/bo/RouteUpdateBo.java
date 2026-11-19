package com.sqx.app.lygl.router.route.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class RouteUpdateBo {

    private Long id;

    /**
     * 路线基本信息id
     */
    @Schema(description = "路线基本信息的唯一标识符", example = "123456")
    private Long baseInfoId;

    /**
     * 天数（第几天）
     */
    @Schema(description = "行程中的天数，表示是第几天的行程", example = "1")
    private Integer number;

    /**
     * 介绍
     */
    @Schema(description = "当天行程的具体介绍和安排", example = "第一天：抵达目的地，入住酒店并休息。")
    private String remark;

    /**
     * 景点id，多个，以逗号分隔
     */
    @Schema(description = "当天计划参观的景点ID，多个ID之间用逗号分隔", example = "789,101112,131415")
    private String scenicSpotId;
}
