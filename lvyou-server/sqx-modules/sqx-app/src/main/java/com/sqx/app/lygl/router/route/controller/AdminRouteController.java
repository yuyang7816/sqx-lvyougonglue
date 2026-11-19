package com.sqx.app.lygl.router.route.controller;

import com.sqx.app.lygl.router.route.domain.bo.RouteInfoBo;
import com.sqx.app.lygl.router.route.domain.bo.RoutePageBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteSaveBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteUpdateBo;
import com.sqx.app.lygl.router.route.service.TravelRouteService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Tag(name = "管理端-路线详情")
@RestController
@RequestMapping("admin/route")
public class AdminRouteController {

    @Resource
    private TravelRouteService routeService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody RouteSaveBo routeSaveBo) {
        return routeService.saveInfo(routeSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody RoutePageBo routePageBo) {
        return routeService.pageInfo(routePageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody RouteInfoBo routeInfoBo) {
        return routeService.info(routeInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody RouteUpdateBo routeUpdateBo) {
        return routeService.updateInfo(routeUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody RouteInfoBo routeInfoBo) {
        return routeService.delete(routeInfoBo);
    }
}
