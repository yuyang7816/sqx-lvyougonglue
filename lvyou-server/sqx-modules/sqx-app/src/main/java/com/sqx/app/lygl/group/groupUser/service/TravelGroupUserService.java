package com.sqx.app.lygl.group.groupUser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserInfoBo;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserPageBo;
import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_group_user(群聊用户信息)】的数据库操作Service
* @createDate 2024-10-12 13:16:01
*/
public interface TravelGroupUserService {

    Result<?> pageInfo(GroupUserPageBo groupUserPageBo);

    Result<?> info(GroupUserInfoBo groupUserInfoBo);
}
