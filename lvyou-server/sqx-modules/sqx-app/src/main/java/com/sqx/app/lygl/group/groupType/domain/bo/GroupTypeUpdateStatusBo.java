package com.sqx.app.lygl.group.groupType.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupTypeUpdateStatusBo {

    @Schema(description = "id")
    private Long id;

    /**
     * 状态（0正常 1禁用）
     */
    @Schema(description = "状态（0正常 1禁用）")
    private Integer status;
}
