package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2026/3/19
 */
@Data
public class ConcertStatisticsVO {

    @Schema(description = "数量")
    private Long count;

    @Schema(description = "地点")
    private String location;
}
