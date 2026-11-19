package com.sqx.app.lygl.router.baseInfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.router.baseInfo.domain.bo.*;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_base_info(路线基本信息)】的数据库操作Service
* @createDate 2024-10-10 09:54:05
*/
public interface TravelBaseInfoService {

    Result<?> saveInfo(BaseInfoSaveBo baseInfoSaveBo);

    Result<?> pageInfo(BaseInfoPageBo baseInfoPageBo);

    Result<BaseInfoVo> info(BaseInfoBo baseInfoBo);

    Result<?> updateInfo(BaseUpdateBo baseUpdateBo);

    Result<?> delete(BaseInfoBo baseInfoBo);

    Result<?> updateStatus(BaseInfoUpdateBo baseInfoUpdateBo);

    Result<IPage<BaseInfoVo>> pageByCity(BaseInfoPageByCityBo baseInfoPageByCityBo);
}
