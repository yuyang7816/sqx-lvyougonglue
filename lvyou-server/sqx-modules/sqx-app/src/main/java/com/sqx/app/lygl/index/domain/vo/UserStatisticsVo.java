package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/28
 */
@Data
public class UserStatisticsVo {

    @Schema(description = "总用户数")
    private Long totalCount;
}
