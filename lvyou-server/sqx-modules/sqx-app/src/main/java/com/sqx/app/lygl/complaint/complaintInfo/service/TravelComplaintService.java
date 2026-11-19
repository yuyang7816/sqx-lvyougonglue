package com.sqx.app.lygl.complaint.complaintInfo.service;

import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.*;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_complaint(用户投诉表)】的数据库操作Service
* @createDate 2024-10-14 10:02:58
*/
public interface TravelComplaintService  {

    Result<?> saveInfo(ComplaintSaveBo complaintSaveBo);

    Result<?> pageInfo(ComplaintPageBo complaintPageBo);

    Result<?> info(ComplaintInfoBo complaintInfoBo);

    Result<?> updateInfo(ComplaintUpdateBo complaintUpdateBo);

    Result<?> delete(ComplaintInfoBo complaintInfoBo);

    Result<?> examine(ComplaintExamineBo complaintExamineBo);
}
