package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2026/3/17
 */
@Data
public class MomentStatisticsVO {

    @Schema(description = "动态总数")
    private Long totalCount;

    @Schema(description = "待审核动态数")
    private Long waitAuditCount;

    @Schema(description = "审核通过数")
    private Long auditCount;

    @Schema(description = "审核未通过数")
    private Long unAuditCount;
}
