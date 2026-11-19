package com.sqx.app.lygl.group.groupInfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.group.groupInfo.domain.bo.*;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import com.sqx.common.core.domain.Result;

/**
* @author Administrator
* @description 针对表【travel_group_info(群信息表)】的数据库操作Service
* @createDate 2024-10-12 10:32:58
*/
public interface TravelGroupInfoService {

    Result<?> saveInfo(GroupInfoSaveBo groupInfoSaveBo);

    Result<?> pageInfo(GroupInfoPageBo groupInfoPageBo);

    Result<?> info(GroupInfoBo groupInfoBo);

    Result<?> updateInfo(GroupInfoUpdateBo groupInfoUpdateBo);

    Result<?> delete(GroupInfoBo groupInfoBo);

    Result<?> join(Long userId, GroupInfoBo groupInfoBo);

    Result<?> updateStatus(GroupInfoUpdateStatusBo groupInfoUpdateStatusBo);

    Result<?> examine(GroupInfoExamineBo groupInfoExamineBo);

    Result<?> owner(GroupOwnerBo groupOwnerBo);

    Result<?> joinList(GroupOwnerBo groupOwnerBo);

    Result<?> enable(GroupEnableBo groupEnableBo);
}
