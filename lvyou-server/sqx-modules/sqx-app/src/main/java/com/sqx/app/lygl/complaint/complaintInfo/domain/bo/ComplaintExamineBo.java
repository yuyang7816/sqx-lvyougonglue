package com.sqx.app.lygl.complaint.complaintInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Data
public class ComplaintExamineBo {

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 状态1等待处理 2封禁账号 3已驳回 4通过但不做处理
     */
    @NotNull(message = "审核状态不能为空")
    @Schema(description = "状态1等待处理 2封禁账号 3已驳回 4通过但不做处理")
    private Integer status;

    /**
     * 处理反馈
     */
    @Schema(description = "处理反馈")
    private String feedback;
}
