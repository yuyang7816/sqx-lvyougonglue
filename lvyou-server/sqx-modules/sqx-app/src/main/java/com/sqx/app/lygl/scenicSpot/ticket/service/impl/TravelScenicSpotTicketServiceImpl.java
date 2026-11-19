package com.sqx.app.lygl.scenicSpot.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.scenicSpot.ticket.db.service.ITravelScenicSpotTicketService;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketInfoBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketPageBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketSaveBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketUpdateBo;
import com.sqx.app.lygl.scenicSpot.ticket.entity.TravelScenicSpotTicket;
import com.sqx.app.lygl.scenicSpot.ticket.service.TravelScenicSpotTicketService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_scenic_spot_ticket(景点门票)】的数据库操作Service实现
* @createDate 2024-10-14 15:30:04
*/
@Service
public class TravelScenicSpotTicketServiceImpl implements TravelScenicSpotTicketService {

    @Resource
    private ITravelScenicSpotTicketService iTravelScenicSpotTicketService;

    @Override
    public Result<?> saveInfo(ScenicSpotTicketSaveBo scenicSpotTicketSaveBo) {
        TravelScenicSpotTicket ticket = new TravelScenicSpotTicket();
        BeanUtils.copyProperties(scenicSpotTicketSaveBo, ticket);

        iTravelScenicSpotTicketService.save(ticket);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ScenicSpotTicketPageBo scenicSpotTicketPageBo) {
        Page<TravelScenicSpotTicket> initPage = PageUtil.initPage(scenicSpotTicketPageBo.getPage(), scenicSpotTicketPageBo.getLimit());

        QueryWrapper<TravelScenicSpotTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelScenicSpotTicket::getScenicSpotId, scenicSpotTicketPageBo.getScenicSpotId());

        IPage<TravelScenicSpotTicket> iPage = iTravelScenicSpotTicketService.page(initPage, queryWrapper);
        return Result.success(iPage);
    }

    @Override
    public Result<?> updateInfo(ScenicSpotTicketUpdateBo scenicSpotTicketUpdateBo) {
        TravelScenicSpotTicket ticket = new TravelScenicSpotTicket();
        BeanUtils.copyProperties(scenicSpotTicketUpdateBo, ticket);

        iTravelScenicSpotTicketService.updateById(ticket);
        return Result.success();
    }

    @Override
    public Result<?> delete(ScenicSpotTicketInfoBo scenicSpotTicketInfoBo) {
        iTravelScenicSpotTicketService.removeById(scenicSpotTicketInfoBo.getId());
        return Result.success();
    }
}




