package com.sqx.app.lygl.complaint.complaintReason.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Data
public class ComplaintReasonUpdateBo {

    @Schema(description = "投诉原因")
    private Long id;

    /**
     * 投诉原因
     */
    @Schema(description = "投诉原因")
    private String reason;
}
