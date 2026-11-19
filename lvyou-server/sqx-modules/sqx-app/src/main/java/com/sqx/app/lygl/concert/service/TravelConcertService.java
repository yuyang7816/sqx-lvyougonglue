package com.sqx.app.lygl.concert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.concert.domain.bo.ConcertInfoBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertPageBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertSaveBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertUpdateBo;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_concert(演唱会)】的数据库操作Service
* @createDate 2024-10-10 14:17:04
*/
public interface TravelConcertService extends IService<TravelConcert> {

    Result<?> saveInfo(ConcertSaveBo concertSaveBo);

    Result<?> pageInfo(ConcertPageBo concertPageBo);

    Result<?> info(ConcertInfoBo concertInfoBo);

    Result<?> updateInfo(ConcertUpdateBo concertUpdateBo);

    Result<?> delete(ConcertInfoBo concertInfoBo);
}
