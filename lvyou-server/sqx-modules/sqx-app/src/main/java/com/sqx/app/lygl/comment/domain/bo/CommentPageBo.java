package com.sqx.app.lygl.comment.domain.bo;

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
public class CommentPageBo extends PageParam {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "关联数据id")
    private Long dataId;

    @Schema(description = "关联数据类型 1动态", example = "1")
    private Integer type;
}
