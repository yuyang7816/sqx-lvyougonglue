package com.sqx.app.lygl.scenicSpot.info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotInfoBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotPageBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotSaveBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotUpdateBo;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_scenic_spot(景点信息)】的数据库操作Service
* @createDate 2024-10-10 10:28:58
*/
public interface TravelScenicSpotService  {

    Result<?> saveInfo(ScenicSpotSaveBo scenicSpotSaveBo);

    Result<IPage<ScenicSpotVo>> pageInfo(Long userId, ScenicSpotPageBo scenicSpotPageBo);

    Result<?> info(Long userId, ScenicSpotInfoBo scenicSpotInfoBo);

    Result<?> updateInfo(ScenicSpotUpdateBo scenicSpotUpdateBo);

    Result<?> delete(ScenicSpotInfoBo scenicSpotInfoBo);
}
