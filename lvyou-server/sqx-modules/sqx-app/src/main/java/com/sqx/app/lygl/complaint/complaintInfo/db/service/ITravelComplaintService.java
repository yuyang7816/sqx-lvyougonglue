package com.sqx.app.lygl.complaint.complaintInfo.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintPageBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.vo.ComplaintVo;
import com.sqx.app.lygl.complaint.complaintInfo.entity.TravelComplaint;


/**
* @author Administrator
* @description 针对表【travel_complaint(用户投诉表)】的数据库操作Service
* @createDate 2024-10-14 10:02:58
*/
public interface ITravelComplaintService extends IService<TravelComplaint> {
    IPage<ComplaintVo> pageInfo(Page<TravelComplaint> initPage, ComplaintPageBo complaintPageBo);
}
