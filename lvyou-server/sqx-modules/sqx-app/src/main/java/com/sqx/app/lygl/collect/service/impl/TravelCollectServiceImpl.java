package com.sqx.app.lygl.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.collect.db.service.ITravelCollectService;
import com.sqx.app.lygl.collect.domain.bo.CollectPageBo;
import com.sqx.app.lygl.collect.domain.bo.CollectSaveBo;
import com.sqx.app.lygl.collect.domain.vo.CollectVo;
import com.sqx.app.lygl.collect.entity.TravelCollect;
import com.sqx.app.lygl.collect.enums.CollectTypeEnum;
import com.sqx.app.lygl.collect.service.TravelCollectService;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.router.baseInfo.db.service.ITravelBaseInfoService;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.app.lygl.shop.shopInfo.db.mapper.TravelShopMapper;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import com.sqx.app.lygl.shop.shopInfo.service.TravelShopService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_collect(用户收藏)】的数据库操作Service实现
* @createDate 2024-10-10 16:09:01
*/
@Service
public class TravelCollectServiceImpl implements TravelCollectService {

    @Resource
    private ITravelCollectService iTravelCollectService;
    @Resource
    private ITravelMomentsService iTravelMomentsService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelBaseInfoService iTravelBaseInfoService;
    @Resource
    private TravelShopService travelShopService;
    @Resource
    private TravelShopMapper travelShopMapper;
    @Resource
    private ITravelLikeService iTravelLikeService;

    @Override
    public Result<?> saveInfo(CollectSaveBo collectSaveBo) {
        QueryWrapper<TravelCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TravelCollect::getUserId, collectSaveBo.getUserId())
                .eq(TravelCollect::getType, collectSaveBo.getType())
                .eq(TravelCollect::getDataId, collectSaveBo.getDataId());
        boolean exists = iTravelCollectService.exists(queryWrapper);

        if (exists) {
            iTravelCollectService.remove(queryWrapper);

            // 商户收藏数 - 1
            travelShopService.subCollect(collectSaveBo.getDataId(), 1);
        } else {
            TravelCollect collect = new TravelCollect();
            BeanUtils.copyProperties(collectSaveBo, collect);

            iTravelCollectService.save(collect);

            // 商户收藏数 + 1
            travelShopService.addCollect(collectSaveBo.getDataId(), 1);
        }

        return Result.success();
    }

    @Override
    public Result<?> pageInfo(CollectPageBo collectPageBo) {
        Page<TravelCollect> initPage = PageUtil.initPage(collectPageBo.getPage(), collectPageBo.getLimit());

        QueryWrapper<TravelCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(collectPageBo.getUserId()), TravelCollect::getUserId, collectPageBo.getUserId())
                .eq(Objects.nonNull(collectPageBo.getType()), TravelCollect::getType, collectPageBo.getType())
                .orderByDesc(TravelCollect::getCreateTime);

        IPage<TravelCollect> collectIPage = iTravelCollectService.page(initPage, queryWrapper);
        if (CollectionUtils.isEmpty(collectIPage.getRecords())) {
            return Result.success(Collections.emptyList());
        }

        List<CollectVo> voList = collectIPage.getRecords().stream().map(item -> {
            CollectVo vo = new CollectVo();
            BeanUtils.copyProperties(item, vo);

            return vo;
        }).collect(Collectors.toList());

        List<Long> dataIdList = voList.stream().map(CollectVo::getDataId).collect(Collectors.toList());
        if (Objects.equals(collectPageBo.getType(), CollectTypeEnum.MOMENTS.code())) {
            List<MomentsVo> momentsList = iTravelMomentsService.listByMomentsId(collectPageBo.getUserId(), dataIdList);

            Map<Long, MomentsVo> momentsMap = momentsList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (CollectVo vo : voList) {
                if (momentsMap.containsKey(vo.getDataId())) {
                    vo.setMoments(momentsMap.get(vo.getDataId()));
                }
            }
        } else if (Objects.equals(collectPageBo.getType(), CollectTypeEnum.SCENIC_SPOT.code())) {
            List<TravelScenicSpot> scenicSpotList = iTravelScenicSpotService.list(new QueryWrapper<TravelScenicSpot>()
                    .lambda()
                    .in(TravelScenicSpot::getId, dataIdList)
            );

            // 景点收藏数
            setScenicCollectNum(scenicSpotList);

            Map<Long, TravelScenicSpot> scenicSpotMap = scenicSpotList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (CollectVo vo : voList) {
                if (scenicSpotMap.containsKey(vo.getDataId())) {
                    vo.setScenicSpot(scenicSpotMap.get(vo.getDataId()));
                }
            }
        } else if (Objects.equals(collectPageBo.getType(), CollectTypeEnum.ROUTE.code())) {
            List<TravelBaseInfo> baseInfoList = iTravelBaseInfoService.list(new QueryWrapper<TravelBaseInfo>()
                    .lambda()
                    .in(TravelBaseInfo::getId, dataIdList)
            );

            // 路线收藏数
            Map<Long, Long> collectMap = getBaseInfoCollectMap(baseInfoList);

            Map<Long, TravelBaseInfo> baseInfoMap = baseInfoList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (CollectVo vo : voList) {
                if (baseInfoMap.containsKey(vo.getDataId())) {
                    vo.setBaseInfo(baseInfoMap.get(vo.getDataId()));
                }

                if (collectMap.containsKey(vo.getDataId())) {
                    vo.setCollectNum(collectMap.get(vo.getDataId()));
                }
            }
        }  else if (Objects.equals(collectPageBo.getType(), CollectTypeEnum.SHOP.code())) {
            List<TravelShop> baseInfoList = travelShopMapper.selectList(new QueryWrapper<TravelShop>()
                    .lambda()
                    .in(TravelShop::getId, dataIdList)
            );

            Map<Long, TravelShop> shopMap = baseInfoList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (CollectVo vo : voList) {
                if (shopMap.containsKey(vo.getDataId())) {
                    vo.setShop(shopMap.get(vo.getDataId()));
                }
            }
        }

        IPage<CollectVo> voIPage = PageUtil.copyPage(collectIPage, voList);
        return Result.success(voIPage);
    }

    private Map<Long, Long> getBaseInfoCollectMap(List<TravelBaseInfo> baseInfoList) {
        if (CollectionUtils.isEmpty(baseInfoList)) {
            return Collections.emptyMap();
        }

        List<Long> baseInfoIds = baseInfoList.stream().map(TravelBaseInfo::getId).collect(Collectors.toList());
        LambdaQueryWrapper<TravelCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TravelCollect::getType, CollectTypeEnum.ROUTE.code())
                .in(TravelCollect::getDataId, baseInfoIds);

        List<TravelCollect> collectList = iTravelCollectService.list(queryWrapper);
        Map<Long, Long> map = collectList.stream()
                .collect(Collectors.groupingBy(TravelCollect::getDataId, Collectors.counting()));

        return map;
    }

    private void setScenicCollectNum(List<TravelScenicSpot> scenicSpotList) {
        if (CollectionUtils.isEmpty(scenicSpotList)) {
            return;
        }

        List<Long> scenicSpotIdList = scenicSpotList.stream().map(TravelScenicSpot::getId).collect(Collectors.toList());
        LambdaQueryWrapper<TravelCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TravelCollect::getType, CollectTypeEnum.SCENIC_SPOT.code())
                .in(TravelCollect::getDataId, scenicSpotIdList);

        List<TravelCollect> collectList = iTravelCollectService.list(queryWrapper);

        Map<Long, Long> map = collectList.stream()
                .collect(Collectors.groupingBy(TravelCollect::getDataId, Collectors.counting()));

        for (TravelScenicSpot travelScenicSpot : scenicSpotList) {
            travelScenicSpot.setCollectNum(map.getOrDefault(travelScenicSpot.getId(), 0L));
        }
    }

    private void setIsLike(List<TravelMoments> momentsList, Long userId) {
        if (CollectionUtils.isEmpty(momentsList)) {
            return;
        }

        if (Objects.isNull(userId)) {
            return;
        }

        List<Long> momentsIds = momentsList.stream().map(TravelMoments::getId).collect(Collectors.toList());

        LambdaQueryWrapper<TravelLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TravelLike::getUserId, userId)
                .in(TravelLike::getId, momentsIds);
        List<TravelLike> travelLikes = iTravelLikeService.list(queryWrapper);

        if (CollectionUtils.isNotEmpty(travelLikes)) {
            Map<Long, Long> travelLikeMap = travelLikes.stream().collect(Collectors.groupingBy(TravelLike::getId, Collectors.counting()));

            for (TravelMoments moments : momentsList) {
                moments.setIsLike(travelLikeMap.containsKey(moments.getId()));
            }
        }
    }
}




