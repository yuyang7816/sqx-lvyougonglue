package com.sqx.app.lygl.group.groupType.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.group.groupType.domain.bo.*;
import com.sqx.app.lygl.group.groupType.entity.TravelGroupType;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_group_type(群类型表)】的数据库操作Service
* @createDate 2024-10-12 10:11:07
*/
public interface TravelGroupTypeService {

    Result<?> saveInfo(GroupTypeSaveBo groupTypeSaveBo);

    Result<?> pageInfo(GroupTypePageBo groupTypePageBo);

    Result<?> info(GroupTypeInfoBo groupTypeInfoBo);

    Result<?> updateInfo(GroupTypeUpdateBo groupTypeUpdateBo);

    Result<?> delete(GroupTypeInfoBo groupTypeInfoBo);

    Result<?> updateStatus(GroupTypeUpdateStatusBo groupTypeUpdateStatusBo);
}
