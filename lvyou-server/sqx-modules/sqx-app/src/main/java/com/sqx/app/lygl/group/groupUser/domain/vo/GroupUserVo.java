package com.sqx.app.lygl.group.groupUser.domain.vo;

import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupUserVo extends TravelGroupUser {

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;

    /**
     * 群聊名称
     */
    @Schema(description = "群聊名称")
    private String groupInfoName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "手机号")
    private String phone;
}
