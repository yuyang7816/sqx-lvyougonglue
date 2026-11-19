package com.sqx.app.lygl.concert.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.concert.db.mapper.TravelConcertMapper;
import com.sqx.app.lygl.concert.domain.bo.ConcertInfoBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertPageBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertSaveBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertUpdateBo;
import com.sqx.app.lygl.concert.domain.vo.ConcertVo;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.app.lygl.concert.service.TravelConcertService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_concert(演唱会)】的数据库操作Service实现
* @createDate 2024-10-10 14:17:04
*/
@Service
public class TravelConcertServiceImpl extends ServiceImpl<TravelConcertMapper, TravelConcert>
    implements TravelConcertService {

    @Override
    public Result<?> saveInfo(ConcertSaveBo concertSaveBo) {
        TravelConcert travelConcert = new TravelConcert();
        BeanUtils.copyProperties(concertSaveBo, travelConcert);

        this.save(travelConcert);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ConcertPageBo concertPageBo) {
        Page<TravelConcert> initPage = PageUtil.initPage(concertPageBo.getPage(), concertPageBo.getLimit());

        QueryWrapper<TravelConcert> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(concertPageBo.getTitle()), TravelConcert::getTitle, concertPageBo.getTitle())
                .like(StringUtils.isNotBlank(concertPageBo.getLocation()), TravelConcert::getLocation, concertPageBo.getLocation())
                .orderByDesc(TravelConcert::getConcertTime);

        IPage<TravelConcert> concertIPage = this.page(initPage, queryWrapper);
        List<ConcertVo> voList = CollectionUtils.isNotEmpty(concertIPage.getRecords()) ? concertIPage.getRecords().stream().map(item -> {
            ConcertVo vo = new ConcertVo();
            BeanUtils.copyProperties(item, vo);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        IPage<ConcertVo> iPage = PageUtil.copyPage(concertIPage, voList);
        return Result.success(iPage);
    }

    @Override
    public Result<?> info(ConcertInfoBo concertInfoBo) {
        TravelConcert concert = this.getById(concertInfoBo.getId());
        if (Objects.isNull(concert)) {
            throw new SqxException("演唱会信息不存在");
        }

        ConcertVo vo = new ConcertVo();
        BeanUtils.copyProperties(concert, vo);
        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(ConcertUpdateBo concertUpdateBo) {
        TravelConcert concert = new TravelConcert();
        BeanUtils.copyProperties(concertUpdateBo, concert);

        this.updateById(concert);
        return Result.success();
    }

    @Override
    public Result<?> delete(ConcertInfoBo concertInfoBo) {
        this.removeById(concertInfoBo.getId());
        return Result.success();
    }
}




