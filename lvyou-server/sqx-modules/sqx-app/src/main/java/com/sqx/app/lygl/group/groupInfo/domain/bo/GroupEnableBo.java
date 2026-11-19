package com.sqx.app.lygl.group.groupInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/26
 */
@Data
public class GroupEnableBo {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "群组id")
    private Long groupId;
}
