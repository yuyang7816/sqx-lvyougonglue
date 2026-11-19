package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/28
 */
@Data
public class IndexStatisticsVo {

    @Schema(description = "总用户数")
    private Long totalUserCount;

    @Schema(description = "今日新增用户数")
    private Long todayUserCount;

    @Schema(description = "本月新增用户数")
    private Long monthUserCount;

    @Schema(description = "本年新增用户数")
    private Long yearUserCount;

    @Schema(description = "景点数")
    private Long scenicSpotCount;

    @Schema(description = "商户数")
    private Long shopCount;

    @Schema(description = "路线数")
    private Long baseInfoCount;
}
