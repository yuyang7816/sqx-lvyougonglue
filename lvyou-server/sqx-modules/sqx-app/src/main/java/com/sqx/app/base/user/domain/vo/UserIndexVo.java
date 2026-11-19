package com.sqx.app.base.user.domain.vo;

import com.sqx.domain.db.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/18
 */
@Data
public class UserIndexVo {

    @Schema(description = "用户信息")
    private UserEntity userEntity;

    @Schema(description = "粉丝数")
    private Long fansCount;

    @Schema(description = "关注数")
    private Long likeCount;

    @Schema(description = "是否关注")
    private Boolean isFollow;
}
