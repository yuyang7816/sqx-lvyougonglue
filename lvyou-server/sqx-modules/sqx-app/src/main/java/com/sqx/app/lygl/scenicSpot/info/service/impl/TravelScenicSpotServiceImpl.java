package com.sqx.app.lygl.scenicSpot.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.collect.db.service.ITravelCollectService;
import com.sqx.app.lygl.collect.entity.TravelCollect;
import com.sqx.app.lygl.collect.enums.CollectTypeEnum;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotInfoBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotPageBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotSaveBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotUpdateBo;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.app.lygl.scenicSpot.info.service.TravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.ticket.db.service.ITravelScenicSpotTicketService;
import com.sqx.app.lygl.scenicSpot.ticket.entity.TravelScenicSpotTicket;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_scenic_spot(景点信息)】的数据库操作Service实现
* @createDate 2024-10-10 10:28:58
*/
@Service
public class TravelScenicSpotServiceImpl implements TravelScenicSpotService {

    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelCollectService iTravelCollectService;
    @Resource
    private ITravelScenicSpotTicketService iTravelScenicSpotTicketService;

    @Override
    public Result<?> saveInfo(ScenicSpotSaveBo scenicSpotSaveBo) {
        TravelScenicSpot scenicSpot = new TravelScenicSpot();
        BeanUtils.copyProperties(scenicSpotSaveBo, scenicSpot);

        iTravelScenicSpotService.save(scenicSpot);
        return Result.success();
    }

    @Override
    public Result<IPage<ScenicSpotVo>> pageInfo(Long userId, ScenicSpotPageBo scenicSpotPageBo) {
        Page<TravelScenicSpot> initPage = PageUtil.initPage(scenicSpotPageBo.getPage(), scenicSpotPageBo.getLimit());

        QueryWrapper<TravelScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(scenicSpotPageBo.getName()), TravelScenicSpot::getName, scenicSpotPageBo.getName())
                .like(StringUtils.isNotBlank(scenicSpotPageBo.getCity()), TravelScenicSpot::getCity, scenicSpotPageBo.getCity())
                .orderByDesc(TravelScenicSpot::getCreateTime);

        IPage<TravelScenicSpot> scenicSpotIPage = iTravelScenicSpotService.page(initPage, queryWrapper);
        List<ScenicSpotVo> voList = CollectionUtils.isNotEmpty(scenicSpotIPage.getRecords()) ? scenicSpotIPage.getRecords().stream().map(item -> {
            ScenicSpotVo vo = new ScenicSpotVo();
            BeanUtils.copyProperties(item, vo);

            long collectNum = iTravelCollectService.count(new LambdaQueryWrapper<TravelCollect>()
                    .eq(TravelCollect::getDataId, item.getId())
                    .eq(TravelCollect::getType, CollectTypeEnum.SCENIC_SPOT.code())
            );

            vo.setCollectNum(collectNum);

            boolean isCollect = iTravelCollectService.exists(new LambdaQueryWrapper<TravelCollect>()
                    .eq(TravelCollect::getDataId, item.getId())
                    .eq(TravelCollect::getType, CollectTypeEnum.SCENIC_SPOT.code())
                    .eq(TravelCollect::getUserId, userId)
            );

            vo.setIsCollect(isCollect);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        IPage<ScenicSpotVo> iPage = PageUtil.copyPage(scenicSpotIPage, voList);
        return Result.success(iPage);
    }

    @Override
    public Result<?> info(Long userId, ScenicSpotInfoBo scenicSpotInfoBo) {
        TravelScenicSpot scenicSpot = iTravelScenicSpotService.getById(scenicSpotInfoBo.getId());
        if (Objects.isNull(scenicSpot)) {
            throw new SqxException("景点信息不存在");
        }

        ScenicSpotVo vo = new ScenicSpotVo();
        BeanUtils.copyProperties(scenicSpot, vo);

        boolean isCollect = iTravelCollectService.exists(new LambdaQueryWrapper<TravelCollect>()
                .eq(TravelCollect::getDataId, vo.getId())
                .eq(TravelCollect::getType, CollectTypeEnum.SCENIC_SPOT.code())
                .eq(TravelCollect::getUserId, userId)
        );

        vo.setIsCollect(isCollect);

        List<TravelScenicSpotTicket> ticketList = iTravelScenicSpotTicketService.list(new LambdaQueryWrapper<TravelScenicSpotTicket>()
                .eq(TravelScenicSpotTicket::getScenicSpotId, scenicSpot.getId())
        );

        vo.setTravelScenicSpotTickets(ticketList);
        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(ScenicSpotUpdateBo scenicSpotUpdateBo) {
        TravelScenicSpot scenicSpot = new TravelScenicSpot();
        BeanUtils.copyProperties(scenicSpotUpdateBo, scenicSpot);

        iTravelScenicSpotService.updateById(scenicSpot);
        return Result.success();
    }

    @Override
    public Result<?> delete(ScenicSpotInfoBo scenicSpotInfoBo) {
        iTravelScenicSpotService.removeById(scenicSpotInfoBo.getId());
        return Result.success();
    }
}




