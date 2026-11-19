package com.sqx.app.lygl.moments.service;

import com.sqx.app.lygl.moments.domain.bo.*;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_moments(动态)】的数据库操作Service
* @createDate 2024-10-10 14:59:26
*/
public interface TravelMomentsService  {

    Result<?> saveInfo(MomentsSaveBo momentsSaveBo);

    Result<?> pageInfo(Long userId, MomentsPageBo momentsPageBo);

    Result<?> info(MomentsInfoBo momentsInfoBo);

    Result<?> updateInfo(MomentsUpdateBo momentsUpdateBo);

    Result<?> delete(MomentsInfoBo momentsInfoBo);

    void audit(MomentsAuditBo momentsAuditBo);
}
