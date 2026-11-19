package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/10/10
 */
@Data
public class MyDataVo {

    @Schema(description = "粉丝")
    private Long fansCount;

    @Schema(description = "关注")
    private Long followCount;

    @Schema(description = "获赞")
    private Long likerCount;
}
