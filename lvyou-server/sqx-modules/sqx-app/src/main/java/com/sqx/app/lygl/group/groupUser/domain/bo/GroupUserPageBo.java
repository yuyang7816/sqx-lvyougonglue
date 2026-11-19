package com.sqx.app.lygl.group.groupUser.domain.bo;

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
public class GroupUserPageBo extends PageParam {

    /**
     * 用户id
     */
    @Schema(description = "用户唯一标识符", example = "12345")
    private Long userId;

    /**
     * 用户名称
     */
    @Schema(description = "用户的名称", example = "张三")
    private String userName;

    /**
     * 群聊id
     */
    @Schema(description = "群聊的唯一标识符", example = "67890")
    private Long groupInfoId;

    /**
     * 群聊名称
     */
    @Schema(description = "群聊的名称", example = "技术讨论群")
    private String groupInfoName;

    /**
     * 用户状态 1正常 2已到期/禁用
     */
    @Schema(description = "用户状态：1表示正常，2表示已到期或禁用", example = "1")
    private Integer status;
}
