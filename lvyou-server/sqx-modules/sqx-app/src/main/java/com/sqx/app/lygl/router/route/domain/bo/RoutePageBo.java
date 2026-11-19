package com.sqx.app.lygl.router.route.domain.bo;


import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoutePageBo extends PageParam {

    /**
     * 路线基本信息id
     */
    @Schema(description = "路线基本信息id")
    private Long baseInfoId;
}
