package com.sqx.app.lygl.concert.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.concert.db.mapper.TravelConcertMapper;
import com.sqx.app.lygl.concert.db.service.ITravelConcertService;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.app.lygl.index.domain.vo.ConcertStatisticsVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【travel_concert(演唱会)】的数据库操作Service实现
 * @createDate 2024-10-10 14:17:04
 */
@Service
public class ITravelConcertServiceImpl extends ServiceImpl<TravelConcertMapper, TravelConcert>
        implements ITravelConcertService {

    @Override
    public List<ConcertStatisticsVO> countByLocaltion(LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.countByLocaltion(startTime, endTime);
    }
}




