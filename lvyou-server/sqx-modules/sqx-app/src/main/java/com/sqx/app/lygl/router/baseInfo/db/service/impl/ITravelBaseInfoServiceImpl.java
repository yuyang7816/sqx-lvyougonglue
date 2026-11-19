package com.sqx.app.lygl.router.baseInfo.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.router.baseInfo.db.mapper.TravelBaseInfoMapper;
import com.sqx.app.lygl.router.baseInfo.db.service.ITravelBaseInfoService;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageBo;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageByCityBo;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.router.baseInfo.service.TravelBaseInfoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_base_info(路线基本信息)】的数据库操作Service实现
* @createDate 2024-10-10 09:54:05
*/
@Service
public class ITravelBaseInfoServiceImpl extends ServiceImpl<TravelBaseInfoMapper, TravelBaseInfo>
    implements ITravelBaseInfoService {

    @Resource
    private TravelBaseInfoMapper travelBaseInfoMapper;

    @Override
    public IPage<BaseInfoVo> pageInfo(Page<TravelBaseInfo> initPage, BaseInfoPageBo baseInfoPageBo) {
        return travelBaseInfoMapper.pageInfo(initPage, baseInfoPageBo);
    }

    @Override
    public BaseInfoVo info(Long currUserId, Long id) {
        return travelBaseInfoMapper.info(currUserId, id);
    }

    @Override
    public IPage<BaseInfoVo> searchRoute(Page<TravelBaseInfo> initPage, Long userId, String keyword) {
        return travelBaseInfoMapper.searchRoute(initPage, userId, keyword);
    }

    @Override
    public IPage<BaseInfoVo> pageByCity(Page<BaseInfoVo> initPage, BaseInfoPageByCityBo baseInfoPageByCityBo) {
        return this.baseMapper.pageByCity(initPage, baseInfoPageByCityBo);
    }
}




