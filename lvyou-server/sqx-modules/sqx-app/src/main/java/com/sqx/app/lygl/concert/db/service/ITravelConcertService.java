package com.sqx.app.lygl.concert.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.app.lygl.index.domain.vo.ConcertStatisticsVO;

import java.time.LocalDateTime;
import java.util.List;


/**
* @author Administrator
* @description 针对表【travel_concert(演唱会)】的数据库操作Service
* @createDate 2024-10-10 14:17:04
*/
public interface ITravelConcertService extends IService<TravelConcert> {

    List<ConcertStatisticsVO> countByLocaltion(LocalDateTime startTime, LocalDateTime endTime);
}
