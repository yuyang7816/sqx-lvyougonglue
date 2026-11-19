package com.sqx.app.lygl.comment.domain.vo;

import com.sqx.app.lygl.comment.entity.TravelComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVo extends TravelComment {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "是否点赞")
    private Boolean isLike;

    @Schema(description = "点赞数")
    private Long likeCount;
}
