package com.sqx.app.lygl.collect.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CollectPageBo extends PageParam {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 1动态 2景点 3路线
     */
    @Schema(description = "1动态 2景点 3路线")
    private Integer type;
}
