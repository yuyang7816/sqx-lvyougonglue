package com.sqx.app.lygl.router.baseInfo.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageBo;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageByCityBo;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;

import javax.validation.constraints.NotNull;


/**
* @author Administrator
* @description 针对表【travel_base_info(路线基本信息)】的数据库操作Service
* @createDate 2024-10-10 09:54:05
*/
public interface ITravelBaseInfoService extends IService<TravelBaseInfo> {

    IPage<BaseInfoVo> pageInfo(Page<TravelBaseInfo> initPage, BaseInfoPageBo baseInfoPageBo);

    BaseInfoVo info(Long currUserId, Long id);

    IPage<BaseInfoVo> searchRoute(Page<TravelBaseInfo> initPage, Long userId, String keyword);

    IPage<BaseInfoVo> pageByCity(Page<BaseInfoVo> initPage, BaseInfoPageByCityBo baseInfoPageByCityBo);
}
