package com.sqx.app.lygl.like.domain.vo;

import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LikeVo extends TravelLike {

    /**
     * 动态
     */
    @Schema(description = "动态")
    private TravelMoments moments;

    /**
     * 评论
     */
    @Schema(description = "评论")
    private TravelComment comment;
}
