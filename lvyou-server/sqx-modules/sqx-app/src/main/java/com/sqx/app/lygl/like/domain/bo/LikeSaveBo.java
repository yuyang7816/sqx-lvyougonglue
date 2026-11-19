package com.sqx.app.lygl.like.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Data
public class LikeSaveBo {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 1动态 2评论
     */
    @Schema(description = "1动态 2评论")
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 关联的id
     */
    @Schema(description = "关联的id")
    @NotNull(message = "关联的id不能为空")
    private Long dataId;
}
