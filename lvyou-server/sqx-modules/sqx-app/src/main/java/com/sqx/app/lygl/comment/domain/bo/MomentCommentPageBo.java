package com.sqx.app.lygl.comment.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/11/17
 */
@Data
public class MomentCommentPageBo extends PageParam {

    @Schema(description = "动态ID")
    private Long momentId;
}
