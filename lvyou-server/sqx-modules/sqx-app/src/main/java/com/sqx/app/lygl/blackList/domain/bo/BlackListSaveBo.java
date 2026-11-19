package com.sqx.app.lygl.blackList.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Data
public class BlackListSaveBo {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 被拉黑的用户id
     */
    @Schema(description = "被拉黑的用户id")
    private Long blackUserId;
}
