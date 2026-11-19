package com.sqx.app.lygl.router.route.service;

import com.sqx.app.lygl.router.route.domain.bo.RouteInfoBo;
import com.sqx.app.lygl.router.route.domain.bo.RoutePageBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteSaveBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteUpdateBo;
import com.sqx.common.core.domain.Result;

/**
* @author Administrator
* @description 针对表【travel_route(行程路线)】的数据库操作Service
* @createDate 2024-10-10 10:12:59
*/
public interface TravelRouteService  {

    Result<?> saveInfo(RouteSaveBo routeSaveBo);

    Result<?> pageInfo(RoutePageBo routePageBo);

    Result<?> info(RouteInfoBo routeInfoBo);

    Result<?> updateInfo(RouteUpdateBo routeUpdateBo);

    Result<?> delete(RouteInfoBo routeInfoBo);
}
