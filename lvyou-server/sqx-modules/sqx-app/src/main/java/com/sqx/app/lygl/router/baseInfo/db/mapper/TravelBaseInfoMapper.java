package com.sqx.app.lygl.router.baseInfo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageBo;
import com.sqx.app.lygl.router.baseInfo.domain.bo.BaseInfoPageByCityBo;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_base_info(路线基本信息)】的数据库操作Mapper
* @createDate 2024-10-10 09:54:05
* @Entity com.sqx.lvyougonglve.entity.TravelBaseInfo
*/
public interface TravelBaseInfoMapper extends BaseMapper<TravelBaseInfo> {

    IPage<BaseInfoVo> pageInfo(Page<TravelBaseInfo> initPage, @Param("param") BaseInfoPageBo baseInfoPageBo);

    BaseInfoVo info(@Param("currUserId") Long currUserId, @Param("id") Long id);

    IPage<BaseInfoVo> searchRoute(Page<TravelBaseInfo> initPage, @Param("userId") Long userId, @Param("keyword") String keyword);

    IPage<BaseInfoVo> pageByCity(Page<BaseInfoVo> initPage, @Param("param") BaseInfoPageByCityBo baseInfoPageByCityBo);
}




