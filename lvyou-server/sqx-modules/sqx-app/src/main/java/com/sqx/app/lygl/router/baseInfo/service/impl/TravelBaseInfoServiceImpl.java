package com.sqx.app.lygl.router.baseInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.utils.CommaSeparatedStringToListConverter;
import com.sqx.app.lygl.collect.db.service.ITravelCollectService;
import com.sqx.app.lygl.collect.entity.TravelCollect;
import com.sqx.app.lygl.collect.enums.CollectTypeEnum;
import com.sqx.app.lygl.router.baseInfo.db.service.ITravelBaseInfoService;
import com.sqx.app.lygl.router.baseInfo.domain.bo.*;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.router.baseInfo.enums.BaseInfoStatus;
import com.sqx.app.lygl.router.baseInfo.service.TravelBaseInfoService;
import com.sqx.app.lygl.router.route.db.service.ITravelRouteService;
import com.sqx.app.lygl.router.route.domain.vo.TravelRouteVo;
import com.sqx.app.lygl.router.route.entity.TravelRoute;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_base_info(路线基本信息)】的数据库操作Service实现
* @createDate 2024-10-10 09:54:05
*/
@Service
public class TravelBaseInfoServiceImpl implements TravelBaseInfoService {

    @Resource
    private ITravelBaseInfoService iTravelBaseInfoService;
    @Resource
    private ITravelRouteService iTravelRouteService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelCollectService iTravelCollectService;

    @Override
    public Result<?> saveInfo(BaseInfoSaveBo baseInfoSaveBo) {
        TravelBaseInfo baseInfo = new TravelBaseInfo();
        BeanUtils.copyProperties(baseInfoSaveBo, baseInfo);
        baseInfo.setStatus(BaseInfoStatus.ENABLE.getStatus());

        iTravelBaseInfoService.save(baseInfo);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(BaseInfoPageBo baseInfoPageBo) {
        Page<TravelBaseInfo> initPage = PageUtil.initPage(baseInfoPageBo.getPage(), baseInfoPageBo.getLimit());

        IPage<BaseInfoVo> iPage = iTravelBaseInfoService.pageInfo(initPage, baseInfoPageBo);
        return Result.success(iPage);
    }

    @Override
    public Result<BaseInfoVo> info(BaseInfoBo baseInfoBo) {
        BaseInfoVo baseInfo = iTravelBaseInfoService.info(baseInfoBo.getCurrUserId(), baseInfoBo.getId());
        if (Objects.isNull(baseInfo)) {
            throw new SqxException("路线信息不存在");
        }

        BaseInfoVo vo = new BaseInfoVo();
        BeanUtils.copyProperties(baseInfo, vo);

        // 路线详情
        List<TravelRouteVo> routeVoList = getRouteList(baseInfoBo.getId());
        vo.setTravelRoute(routeVoList);
        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(BaseUpdateBo baseUpdateBo) {
        TravelBaseInfo baseInfo = new TravelBaseInfo();
        BeanUtils.copyProperties(baseUpdateBo, baseInfo);

        iTravelBaseInfoService.updateById(baseInfo);
        return Result.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result<?> delete(BaseInfoBo baseInfoBo) {
        // 删除路线详情
        QueryWrapper<TravelRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelRoute::getBaseInfoId, baseInfoBo.getId());
        iTravelRouteService.remove(queryWrapper);

        // 删除路线基本信息
        iTravelBaseInfoService.removeById(baseInfoBo.getId());
        return Result.success();
    }

    @Override
    public Result<?> updateStatus(BaseInfoUpdateBo baseInfoUpdateBo) {
        UpdateWrapper<TravelBaseInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelBaseInfo::getId, baseInfoUpdateBo.getId())
                .set(TravelBaseInfo::getStatus, baseInfoUpdateBo.getStatus());

        iTravelBaseInfoService.update(new TravelBaseInfo(), updateWrapper);
        return Result.success();
    }

    @Override
    public Result<IPage<BaseInfoVo>> pageByCity(BaseInfoPageByCityBo baseInfoPageByCityBo) {
        Page<BaseInfoVo> initPage = PageUtil.initPage(baseInfoPageByCityBo.getPage(), baseInfoPageByCityBo.getLimit());

        IPage<BaseInfoVo> voIPage = iTravelBaseInfoService.pageByCity(initPage, baseInfoPageByCityBo);
        return Result.success(voIPage);
    }

    /**
     * 路线详情
     * @param baseInfoId 路线基本信息
     */
    private List<TravelRouteVo> getRouteList(Long baseInfoId) {
        QueryWrapper<TravelRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelRoute::getBaseInfoId, baseInfoId);
        List<TravelRoute> routeList = iTravelRouteService.list(queryWrapper);

        List<TravelRouteVo> routeVoList = CollectionUtils.isNotEmpty(routeList) ? routeList.stream().map(item -> {
            TravelRouteVo vo = new TravelRouteVo();
            BeanUtils.copyProperties(item,vo);

            List<ScenicSpotVo> scenicSpotVoList = getScenicSpotList(item.getScenicSpotId());

            vo.setScenicSpotVoList(scenicSpotVoList);
            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        return routeVoList;
    }

    /**
     * 景点列表
     * @param scenicSpotIds 路线详情ids
     */
    private List<ScenicSpotVo> getScenicSpotList(String scenicSpotIds) {
        if (StringUtils.isBlank(scenicSpotIds)) {
            return Collections.emptyList();
        }

        List<String> scenicSpotIdList = CommaSeparatedStringToListConverter.convert(scenicSpotIds);

        QueryWrapper<TravelScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(TravelScenicSpot::getId, scenicSpotIdList);

        List<TravelScenicSpot> scenicSpotList = iTravelScenicSpotService.list(queryWrapper);
        List<ScenicSpotVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(scenicSpotList)) {
            Map<Long, Long> scenicSpotCollectMap =  countCollectScenicSpot(scenicSpotIdList);

            for (TravelScenicSpot travelScenicSpot : scenicSpotList) {
                ScenicSpotVo vo = new ScenicSpotVo();
                BeanUtils.copyProperties(travelScenicSpot, vo);

                if (scenicSpotCollectMap.containsKey(travelScenicSpot.getId())) {
                    vo.setCollectNum(scenicSpotCollectMap.get(travelScenicSpot.getId()));
                }

                voList.add(vo);
            }
        }

        return voList;
    }

    private Map<Long, Long> countCollectScenicSpot(List<String> scenicSpotIdList) {
        if (CollectionUtils.isEmpty(scenicSpotIdList)) {
            return Collections.emptyMap();
        }

        LambdaQueryWrapper<TravelCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(TravelCollect::getDataId, scenicSpotIdList)
                .eq(TravelCollect::getType, CollectTypeEnum.SCENIC_SPOT.code());

        List<TravelCollect> collectList = iTravelCollectService.list(queryWrapper);

        return collectList
                .stream()
                .collect(Collectors.groupingBy(TravelCollect::getDataId, Collectors.counting()));
    }
}




