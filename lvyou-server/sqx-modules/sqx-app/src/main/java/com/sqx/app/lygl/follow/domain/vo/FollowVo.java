package com.sqx.app.lygl.follow.domain.vo;

import com.sqx.app.lygl.follow.entity.TravelFollow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FollowVo extends TravelFollow {

    /**
     * 用户名称
     */
    @Schema(description = "用户的名字")
    private String userName;

    /**
     * 用户头像
     */
    @Schema(description = "用户的个人头像URL")
    private String avatar;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "年龄")
    private Integer age;

    /**
     * 关注用户名称
     */
    @Schema(description = "被关注用户的名字")
    private String targetUserName;

    /**
     * 关注用户头像
     */
    @Schema(description = "被关注用户的个人头像URL")
    private String targetUserAvatar;

    @Schema(description = "被关注用户的性别")
    private Integer targetUserSex;

    @Schema(description = "被关注用户的年龄")
    private Integer targetUserAge;
}
