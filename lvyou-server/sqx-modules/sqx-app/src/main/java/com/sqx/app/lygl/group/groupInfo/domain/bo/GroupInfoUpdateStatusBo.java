package com.sqx.app.lygl.group.groupInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupInfoUpdateStatusBo {

    @Schema(description = "id")
    private Long id;

    /**
     * 群状态（0正常 1禁用 2过期 3人数已达最大）
     */
    @Schema(description = "群状态（0正常 1禁用 2过期 3人数已达最大）")
    private Integer status;
}
