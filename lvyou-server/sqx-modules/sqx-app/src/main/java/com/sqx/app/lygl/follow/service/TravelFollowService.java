package com.sqx.app.lygl.follow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.follow.domain.bo.FollowPageBo;
import com.sqx.app.lygl.follow.domain.bo.FollowSaveBo;
import com.sqx.app.lygl.follow.entity.TravelFollow;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_follow(用户关注)】的数据库操作Service
* @createDate 2024-10-12 15:12:55
*/
public interface TravelFollowService extends IService<TravelFollow> {

    Result<?> saveInfo(FollowSaveBo followSaveBo);

    Result<?> pageInfo(FollowPageBo followPageBo);
}
