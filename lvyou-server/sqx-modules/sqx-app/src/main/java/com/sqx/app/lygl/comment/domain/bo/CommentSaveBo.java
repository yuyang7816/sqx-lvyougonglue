package com.sqx.app.lygl.comment.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Data
public class CommentSaveBo {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "关联数据id")
    private Long dataId;

    @Schema(description = "关联数据类型 1动态", example = "1")
    private Integer type;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "评论图片，支持多个，逗号分隔")
    private String contentImg;
}
