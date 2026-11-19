package com.sqx.app.lygl.complaint.complaintInfo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintPageBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.vo.ComplaintVo;
import com.sqx.app.lygl.complaint.complaintInfo.entity.TravelComplaint;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_complaint(用户投诉表)】的数据库操作Mapper
* @createDate 2024-10-14 10:02:58
* @Entity com.sqx.lvyougonglve.complaintInfo.entity.TravelComplaint
*/
public interface TravelComplaintMapper extends BaseMapper<TravelComplaint> {

    IPage<ComplaintVo> pageInfo(Page<TravelComplaint> initPage, @Param("param") ComplaintPageBo complaintPageBo);
}




