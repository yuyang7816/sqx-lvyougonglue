package com.sqx.app.lygl.comment.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Data
public class CommentInfoBo {

    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;
}
