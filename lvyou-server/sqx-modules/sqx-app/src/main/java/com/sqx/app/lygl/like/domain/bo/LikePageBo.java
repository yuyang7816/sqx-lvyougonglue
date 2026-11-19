package com.sqx.app.lygl.like.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LikePageBo extends PageParam {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 1动态 2评论
     */
    @Schema(description = "1动态 2评论")
    private Integer type;
}
