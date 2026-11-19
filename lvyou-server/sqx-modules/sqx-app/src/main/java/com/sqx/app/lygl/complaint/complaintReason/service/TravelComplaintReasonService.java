package com.sqx.app.lygl.complaint.complaintReason.service;

import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonInfoBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonPageBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonSaveBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonUpdateBo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_complaint_reason(投诉原因)】的数据库操作Service
* @createDate 2024-10-14 09:42:10
*/
public interface TravelComplaintReasonService {

    Result<?> saveInfo(ComplaintReasonSaveBo complaintReasonSaveBo);

    Result<?> pageInfo(ComplaintReasonPageBo complaintReasonPageBo);

    Result<?> info(ComplaintReasonInfoBo complaintReasonInfoBo);

    Result<?> updateInfo(ComplaintReasonUpdateBo complaintReasonUpdateBo);

    Result<?> delete(ComplaintReasonInfoBo complaintReasonInfoBo);
}
