package com.sqx.app.lygl.complaint.complaintReason.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.complaint.complaintReason.db.service.ITravelComplaintReasonService;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonInfoBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonPageBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonSaveBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonUpdateBo;
import com.sqx.app.lygl.complaint.complaintReason.entity.TravelComplaintReason;
import com.sqx.app.lygl.complaint.complaintReason.service.TravelComplaintReasonService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【travel_complaint_reason(投诉原因)】的数据库操作Service实现
* @createDate 2024-10-14 09:42:10
*/
@Service
public class TravelComplaintReasonServiceImpl implements TravelComplaintReasonService {

    @Resource
    private ITravelComplaintReasonService iTravelComplaintReasonService;

    @Override
    public Result<?> saveInfo(ComplaintReasonSaveBo complaintReasonSaveBo) {
        boolean exists = iTravelComplaintReasonService.exists(new QueryWrapper<TravelComplaintReason>()
                .lambda()
                .eq(TravelComplaintReason::getReason, complaintReasonSaveBo.getReason())
        );
        if (exists) {
            throw new SqxException("投诉原因已存在");
        }

        TravelComplaintReason complaintReason = new TravelComplaintReason();
        BeanUtils.copyProperties(complaintReasonSaveBo, complaintReason);

        iTravelComplaintReasonService.save(complaintReason);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ComplaintReasonPageBo complaintReasonPageBo) {
        Page<TravelComplaintReason> initPage = PageUtil.initPage(complaintReasonPageBo.getPage(), complaintReasonPageBo.getLimit());

        QueryWrapper<TravelComplaintReason> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(StringUtils.isNotBlank(complaintReasonPageBo.getReason()), TravelComplaintReason::getReason,complaintReasonPageBo.getReason());

        IPage<TravelComplaintReason> iPage = iTravelComplaintReasonService.page(initPage, queryWrapper);
        return Result.success(iPage);
    }

    @Override
    public Result<?> info(ComplaintReasonInfoBo complaintReasonInfoBo) {
        TravelComplaintReason complaintReason = iTravelComplaintReasonService.getById(complaintReasonInfoBo.getId());
        if (Objects.isNull(complaintReason)) {
            throw new SqxException("投诉原因不存在");
        }

        return Result.success(complaintReason);
    }

    @Override
    public Result<?> updateInfo(ComplaintReasonUpdateBo complaintReasonUpdateBo) {
        TravelComplaintReason complaintReason = new TravelComplaintReason();
        BeanUtils.copyProperties(complaintReasonUpdateBo, complaintReason);

        iTravelComplaintReasonService.updateById(complaintReason);
        return Result.success();
    }

    @Override
    public Result<?> delete(ComplaintReasonInfoBo complaintReasonInfoBo) {
        iTravelComplaintReasonService.removeById(complaintReasonInfoBo.getId());
        return Result.success();
    }
}




