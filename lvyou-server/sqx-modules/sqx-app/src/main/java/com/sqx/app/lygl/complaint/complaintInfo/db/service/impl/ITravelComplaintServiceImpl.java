package com.sqx.app.lygl.complaint.complaintInfo.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.complaint.complaintInfo.db.mapper.TravelComplaintMapper;
import com.sqx.app.lygl.complaint.complaintInfo.db.service.ITravelComplaintService;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintPageBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.vo.ComplaintVo;
import com.sqx.app.lygl.complaint.complaintInfo.entity.TravelComplaint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_complaint(用户投诉表)】的数据库操作Service实现
* @createDate 2024-10-14 10:02:58
*/
@Service
public class ITravelComplaintServiceImpl extends ServiceImpl<TravelComplaintMapper, TravelComplaint>
    implements ITravelComplaintService {

    @Resource
    private TravelComplaintMapper travelComplaintMapper;

    @Override
    public IPage<ComplaintVo> pageInfo(Page<TravelComplaint> initPage, ComplaintPageBo complaintPageBo) {
        return travelComplaintMapper.pageInfo(initPage, complaintPageBo);
    }
}




