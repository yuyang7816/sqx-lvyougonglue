package com.sqx.app.lygl.collect.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Data
public class CollectSaveBo {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 1动态 2景点 3路线 4商户
     */
    @Schema(description = "1动态 2景点 3路线 4商户")
    private Integer type;

    /**
     * 关联的id
     */
    @Schema(description = "关联的id")
    private Long dataId;
}
