package com.sqx.app.lygl.moments.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/18
 */
@Data
public class MomentsAuditBo {

    @Schema(description = "动态id")
    private Long id;

    @Schema(description = "审核状态：0-待审核, 1-审核通过, 2-审核拒绝")
    private Integer auditStatus;

    @Schema(description = "审核理由")
    private String auditReason;
}
