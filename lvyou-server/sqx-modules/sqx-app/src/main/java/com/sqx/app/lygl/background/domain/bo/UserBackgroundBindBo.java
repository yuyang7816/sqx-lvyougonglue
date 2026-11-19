package com.sqx.app.lygl.background.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/18
 */
@Data
public class UserBackgroundBindBo {

    @Schema(description = "用户id")
    private Long uerId;

    @Schema(description = "图片地址")
    private String imgUrl;
}
