package com.sqx.app.lygl.background.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
@Data
public class UserBackgroundUpdateBo {
    @Schema(description = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "用户id 0表示系统背景")
    private Long userId;

    @Schema(description = "背景图地址")
    private String imgUrl;
}
