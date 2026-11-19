package com.sqx.app.lygl.router.route.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.utils.CommaSeparatedStringToListConverter;
import com.sqx.app.lygl.router.route.db.service.ITravelRouteService;
import com.sqx.app.lygl.router.route.domain.bo.RouteInfoBo;
import com.sqx.app.lygl.router.route.domain.bo.RoutePageBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteSaveBo;
import com.sqx.app.lygl.router.route.domain.bo.RouteUpdateBo;
import com.sqx.app.lygl.router.route.domain.vo.TravelRouteVo;
import com.sqx.app.lygl.router.route.entity.TravelRoute;
import com.sqx.app.lygl.router.route.service.TravelRouteService;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_route(行程路线)】的数据库操作Service实现
* @createDate 2024-10-10 10:12:59
*/
@Service
public class TravelRouteServiceImpl implements TravelRouteService {

    @Resource
    private ITravelRouteService iTravelRouteService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;

    @Override
    public Result<?> saveInfo(RouteSaveBo routeSaveBo) {
        TravelRoute travelRoute = new TravelRoute();
        BeanUtils.copyProperties(routeSaveBo, travelRoute);

        iTravelRouteService.save(travelRoute);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(RoutePageBo routePageBo) {
        Page<TravelRoute> initPage = PageUtil.initPage(routePageBo.getPage(), routePageBo.getLimit());

        QueryWrapper<TravelRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(routePageBo.getBaseInfoId()), TravelRoute::getBaseInfoId, routePageBo.getBaseInfoId())
                .orderByDesc(TravelRoute::getCreateTime);

        IPage<TravelRoute> routeIPage = iTravelRouteService.page(initPage, queryWrapper);
        List<TravelRouteVo> voList = CollectionUtils.isNotEmpty(routeIPage.getRecords()) ? routeIPage.getRecords().stream().map(item -> {
            TravelRouteVo vo = new TravelRouteVo();
            BeanUtils.copyProperties(item, vo);

            List<ScenicSpotVo> scenicSpotVoList = getScenicSpotList(item.getScenicSpotId());

            vo.setScenicSpotVoList(scenicSpotVoList);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        return Result.success(voList);
    }

    @Override
    public Result<?> info(RouteInfoBo routeInfoBo) {
        TravelRoute travelRoute = iTravelRouteService.getById(routeInfoBo.getId());
        if (Objects.isNull(travelRoute)) {
            throw new SqxException("路线详情不存在");
        }

        TravelRouteVo vo = new TravelRouteVo();
        BeanUtils.copyProperties(travelRoute, vo);

        List<ScenicSpotVo> scenicSpotList = getScenicSpotList(travelRoute.getScenicSpotId());
        vo.setScenicSpotVoList(scenicSpotList);

        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(RouteUpdateBo routeUpdateBo) {
        TravelRoute travelRoute = new TravelRoute();
        BeanUtils.copyProperties(routeUpdateBo, travelRoute);

        iTravelRouteService.updateById(travelRoute);
        return Result.success();
    }

    @Override
    public Result<?> delete(RouteInfoBo routeInfoBo) {
        iTravelRouteService.removeById(routeInfoBo.getId());
        return Result.success();
    }

    /**
     * 景点列表
     * @param scenicSpotIds 路线详情ids
     */
    private List<ScenicSpotVo> getScenicSpotList(String scenicSpotIds) {
        List<String> scenicSpotIdList = CommaSeparatedStringToListConverter.convert(scenicSpotIds);
        if (CollectionUtils.isEmpty(scenicSpotIdList)) {
            return Collections.emptyList();
        }

        QueryWrapper<TravelScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(TravelScenicSpot::getId, scenicSpotIdList);

        List<TravelScenicSpot> scenicSpotList = iTravelScenicSpotService.list(queryWrapper);
        List<ScenicSpotVo> voList = CollectionUtils.isNotEmpty(scenicSpotList) ? scenicSpotList.stream().map(item -> {
            ScenicSpotVo vo = new ScenicSpotVo();
            BeanUtils.copyProperties(item, vo);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        return voList;
    }
}




