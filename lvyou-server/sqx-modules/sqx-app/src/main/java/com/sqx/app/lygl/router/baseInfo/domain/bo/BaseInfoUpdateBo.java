package com.sqx.app.lygl.router.baseInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class BaseInfoUpdateBo {

    @Schema(description = "id")
    private Long id;

    /**
     * 状态 1正常 2禁用
     */
    @Schema(description = "状态 1正常 2禁用")
    private Integer status;
}
