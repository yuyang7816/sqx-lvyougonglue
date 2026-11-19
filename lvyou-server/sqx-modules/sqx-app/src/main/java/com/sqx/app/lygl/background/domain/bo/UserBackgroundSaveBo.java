package com.sqx.app.lygl.background.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
@Data
public class UserBackgroundSaveBo {
    @Schema(description = "用户id 0表示系统背景")
    private Long userId;

    @Schema(description = "背景图地址")
    private String imgUrl;
}
