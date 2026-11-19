package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2026/3/19
 */
@Data
public class ExamineStatisticsVO {

    @Schema(description = "动态待审核")
    private Long momentCount;

    @Schema(description = "群组待审核")
    private Long groupCount;
}
