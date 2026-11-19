package com.sqx.app.lygl.router.route.domain.vo;


import com.sqx.app.lygl.router.route.entity.TravelRoute;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TravelRouteVo extends TravelRoute {

    /**
     * 景点列表
     */
    private List<ScenicSpotVo> scenicSpotVoList;
}
