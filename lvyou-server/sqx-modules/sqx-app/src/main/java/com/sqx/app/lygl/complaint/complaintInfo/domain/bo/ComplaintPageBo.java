package com.sqx.app.lygl.complaint.complaintInfo.domain.bo;

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
public class ComplaintPageBo extends PageParam {

    /**
     * 投诉用户
     */
    @Schema(description = "投诉用户的ID")
    private Long userId;

    /**
     * 1用户 2群聊
     */
    @Schema(description = "投诉目标类型：1代表用户，2代表群聊", example = "1")
    private Integer targetType;

    /**
     * 投诉原因
     */
    @Schema(description = "投诉的原因描述")
    private String reason;

    /**
     * 状态1等待处理 2封禁账号 3已驳回 4通过但不做处理
     */
    @Schema(description = "投诉状态：1-等待处理，2-封禁账号，3-已驳回，4-通过但不做处理", example = "1")
    private Integer status;
}
