package com.sqx.app.lygl.follow.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FollowPageBo extends PageParam {

    /**
     * 用户ID
     */
    @Schema(description = "用户的唯一标识符")
    private Long userId;

    /**
     * 关注用户ID
     */
    @Schema(description = "被关注用户的唯一标识符")
    private Long targetUserId;

    /**
     * 用户名称
     */
    @Schema(description = "用户的名字")
    private String userName;

    /**
     * 关注用户名称
     */
    @Schema(description = "被关注用户的名字")
    private String targetUserName;
}
