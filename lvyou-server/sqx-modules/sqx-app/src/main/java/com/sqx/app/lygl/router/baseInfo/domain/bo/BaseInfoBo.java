package com.sqx.app.lygl.router.baseInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class BaseInfoBo {

    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;

    private Long currUserId;
}
