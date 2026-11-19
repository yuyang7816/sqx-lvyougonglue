package com.sqx.app.lygl.group.groupInfo.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/26
 */
@Data
public class GroupOwnerBo extends PageParam {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "群状态（0正常 1禁用 2过期 3人数已达最大）")
    private Integer status;

    @Schema(description = "审核状态（0待审核 1审核通过 2审核拒绝）")
    private Integer auditStatus;
}
