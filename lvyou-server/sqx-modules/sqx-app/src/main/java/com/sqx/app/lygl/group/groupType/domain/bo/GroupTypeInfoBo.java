package com.sqx.app.lygl.group.groupType.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupTypeInfoBo {

    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;
}
