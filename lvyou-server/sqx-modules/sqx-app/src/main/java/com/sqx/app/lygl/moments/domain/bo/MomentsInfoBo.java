package com.sqx.app.lygl.moments.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class MomentsInfoBo {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;
}
