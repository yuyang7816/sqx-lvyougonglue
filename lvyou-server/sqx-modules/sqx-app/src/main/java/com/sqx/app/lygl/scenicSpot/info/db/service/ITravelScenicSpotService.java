package com.sqx.app.lygl.scenicSpot.info.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;

import java.util.List;

/**
* @author Administrator
* @description 针对表【travel_scenic_spot(景点信息)】的数据库操作Service
* @createDate 2024-10-10 10:28:58
*/
public interface ITravelScenicSpotService extends IService<TravelScenicSpot> {

    IPage<ScenicSpotVo> searchScenicSpot(Page<TravelScenicSpot> initPage, Long userId, String keyword);
}
