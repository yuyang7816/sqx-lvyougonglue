package com.sqx.app.lygl.comment.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Data
public class CommentUpdateBo {

    @NotNull(message = "id不能为空")
    @Schema(description = "唯一标识符", required = true)
    private Long id;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 关联数据id
     */
    @Schema(description = "关联数据id")
    private Long dataId;

    /**
     * 关联数据类型 1动态
     */
    @Schema(description = "关联数据类型 1动态", example = "1")
    private Integer type;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 评论图片，支持多个，逗号分隔
     */
    @Schema(description = "评论图片，支持多个，逗号分隔")
    private String contentImg;
}
