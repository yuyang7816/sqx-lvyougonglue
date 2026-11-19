package com.sqx.app.lygl.follow.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class FollowSaveBo {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 关注用户ID
     */
    @Schema(description = "关注用户ID")
    private Long targetUserId;
}
