package com.sqx.app.lygl.concert.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.app.lygl.index.domain.vo.ConcertStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Administrator
 * @description 针对表【travel_concert(演唱会)】的数据库操作Mapper
 * @createDate 2024-10-10 14:17:04
 * @Entity com.sqx.lvyougonglve.concert.entity.TravelConcert
 */
public interface TravelConcertMapper extends BaseMapper<TravelConcert> {

    List<ConcertStatisticsVO> countByLocaltion(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}




