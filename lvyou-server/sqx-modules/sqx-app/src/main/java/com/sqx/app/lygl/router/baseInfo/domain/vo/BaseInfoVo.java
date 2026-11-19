package com.sqx.app.lygl.router.baseInfo.domain.vo;


import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.router.route.domain.vo.TravelRouteVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInfoVo extends TravelBaseInfo {

    /**
     * 旅游路线
     */
    @Schema(description = "旅游路线")
    private List<TravelRouteVo> travelRoute;

    @Schema(description = "收藏人数")
    private Long collectCount;

    @Schema(description = "是否收藏")
    private Boolean isCollect;
}
