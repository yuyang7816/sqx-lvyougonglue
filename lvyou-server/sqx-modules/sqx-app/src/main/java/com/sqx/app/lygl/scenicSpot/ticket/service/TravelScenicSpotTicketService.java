package com.sqx.app.lygl.scenicSpot.ticket.service;

import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketInfoBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketPageBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketSaveBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketUpdateBo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_scenic_spot_ticket(景点门票)】的数据库操作Service
* @createDate 2024-10-14 15:30:04
*/
public interface TravelScenicSpotTicketService {

    Result<?> saveInfo(ScenicSpotTicketSaveBo scenicSpotTicketSaveBo);

    Result<?> pageInfo(ScenicSpotTicketPageBo scenicSpotTicketPageBo);

    Result<?> updateInfo(ScenicSpotTicketUpdateBo scenicSpotTicketUpdateBo);

    Result<?> delete(ScenicSpotTicketInfoBo scenicSpotTicketInfoBo);
}
