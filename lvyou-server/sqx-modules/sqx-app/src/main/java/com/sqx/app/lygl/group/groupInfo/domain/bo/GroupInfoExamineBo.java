package com.sqx.app.lygl.group.groupInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupInfoExamineBo {

    @Schema(description = "唯一标识符")
    private Long id;

    /**
     * 审核状态（0待审核 1审核通过 2审核拒绝）
     */
    @Schema(description = "审核状态: 0-待审核, 1-审核通过, 2-审核拒绝", allowableValues = {"0", "1", "2"})
    private Integer auditStatus;

    /**
     * 审核备注
     */
    @Schema(description = "审核备注信息", example = "资料齐全，批准通过")
    private String auditRemark;

}
