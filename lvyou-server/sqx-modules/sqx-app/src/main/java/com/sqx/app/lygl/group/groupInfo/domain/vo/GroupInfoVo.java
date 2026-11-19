package com.sqx.app.lygl.group.groupInfo.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupInfoVo extends TravelGroupInfo {

    /**
     * 群聊类型名称
     */
    @Schema(description = "群聊类型名称")
    private String typeName;

    /**
     * 群聊所属用户名称
     */
    @Schema(description = "群聊所属用户名称")
    private String userName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    /**
     * 是否加入群组
     */
    @Schema(description = "是否加入群组")
    private Boolean isJoinGroup;

    @Schema(description = "群聊创建天数")
    private Long days;

    @Schema(description = "加入人数")
    private Long joinNum;
}
