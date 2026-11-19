package com.sqx.app.lygl.complaint.complaintReason.domain.bo;


import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ComplaintReasonPageBo extends PageParam {

    /**
     * 投诉原因
     */
    @Schema(description = "投诉原因")
    private String reason;
}
