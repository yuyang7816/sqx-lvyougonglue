package com.sqx.app.lygl.collect.service;

import com.sqx.app.lygl.collect.domain.bo.CollectPageBo;
import com.sqx.app.lygl.collect.domain.bo.CollectSaveBo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_collect(用户收藏)】的数据库操作Service
* @createDate 2024-10-10 16:09:01
*/
public interface TravelCollectService {

    Result<?> saveInfo(CollectSaveBo collectSaveBo);

    Result<?> pageInfo(CollectPageBo collectPageBo);
}
