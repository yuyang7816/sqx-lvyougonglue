package com.sqx.app.lygl.complaint.complaintInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.utils.CommaSeparatedStringToListConverter;
import com.sqx.app.lygl.complaint.complaintInfo.db.service.ITravelComplaintService;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.*;
import com.sqx.app.lygl.complaint.complaintInfo.domain.vo.ComplaintVo;
import com.sqx.app.lygl.complaint.complaintInfo.entity.TravelComplaint;
import com.sqx.app.lygl.complaint.complaintInfo.enums.ComplaintStatus;
import com.sqx.app.lygl.complaint.complaintInfo.enums.ComplaintTargetTypeEnum;
import com.sqx.app.lygl.complaint.complaintInfo.service.TravelComplaintService;
import com.sqx.app.lygl.group.groupInfo.db.service.ITravelGroupInfoService;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import com.sqx.app.lygl.group.groupInfo.enums.GroupStatus;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import com.sqx.domain.db.entity.UserEntity;
import com.sqx.domain.db.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_complaint(用户投诉表)】的数据库操作Service实现
* @createDate 2024-10-14 10:02:58
*/
@Service
public class TravelComplaintServiceImpl implements TravelComplaintService {

    @Resource
    private ITravelComplaintService iTravelComplaintService;
    @Resource
    private IUserService iUserService;
    @Resource
    private ITravelGroupInfoService iTravelGroupInfoService;

    @Override
    public Result<?> saveInfo(ComplaintSaveBo complaintSaveBo) {
        TravelComplaint complaint = new TravelComplaint();
        BeanUtils.copyProperties(complaintSaveBo, complaint);
        complaint.setStatus(ComplaintStatus.WAIT.code());

        iTravelComplaintService.save(complaint);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ComplaintPageBo complaintPageBo) {
        Page<TravelComplaint> initPage = PageUtil.initPage(complaintPageBo.getPage(), complaintPageBo.getLimit());

        IPage<ComplaintVo> complaintIPage = iTravelComplaintService.pageInfo(initPage, complaintPageBo);

        List<ComplaintVo> voList = CollectionUtils.isNotEmpty(complaintIPage.getRecords()) ? complaintIPage.getRecords().stream().map(item -> {
            item.setImgList(CommaSeparatedStringToListConverter.convert(item.getImgUrl()));
            return item;
        }).collect(Collectors.toList()) : Collections.emptyList();

        IPage<ComplaintVo> voIPage = PageUtil.copyPage(complaintIPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result<?> info(ComplaintInfoBo complaintInfoBo) {
        TravelComplaint complaint = iTravelComplaintService.getById(complaintInfoBo.getId());
        if (Objects.isNull(complaint)) {
            throw new SqxException("投诉信息不存在");
        }

        ComplaintVo vo = new ComplaintVo();
        BeanUtils.copyProperties(complaint, vo);
        vo.setImgList(CommaSeparatedStringToListConverter.convert(complaint.getImgUrl()));

        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(ComplaintUpdateBo complaintUpdateBo) {
        TravelComplaint complaint = new TravelComplaint();
        BeanUtils.copyProperties(complaintUpdateBo, complaint);

        iTravelComplaintService.updateById(complaint);
        return Result.success();
    }

    @Override
    public Result<?> delete(ComplaintInfoBo complaintInfoBo) {
        iTravelComplaintService.removeById(complaintInfoBo.getId());
        return Result.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result<?> examine(ComplaintExamineBo complaintExamineBo) {
        TravelComplaint complaint = iTravelComplaintService.getById(complaintExamineBo.getId());
        if (Objects.isNull(complaint)) {
            throw new SqxException("投诉信息不存在");
        }

        // 修改投诉信息
        UpdateWrapper<TravelComplaint> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelComplaint::getId, complaintExamineBo.getId())
                .set(TravelComplaint::getStatus, complaintExamineBo.getStatus())
                .set(StringUtils.isNotBlank(complaintExamineBo.getFeedback()), TravelComplaint::getFeedback, complaintExamineBo.getFeedback());

        iTravelComplaintService.update(new TravelComplaint(), updateWrapper);

        if (Objects.equals(complaintExamineBo.getStatus(), ComplaintStatus.BLOCK.code())) {
            // 封禁用户
            if (Objects.equals(complaint.getTargetType(), ComplaintTargetTypeEnum.USER.code())) {
                iUserService.update(new UpdateWrapper<UserEntity>()
                        .lambda()
                        .eq(UserEntity::getUserId, complaint.getTargetId())
                        .set(UserEntity::getStatus, 2)
                );
            }

            // 封禁群聊
            if (Objects.equals(complaint.getTargetType(), ComplaintTargetTypeEnum.GROUP.code())) {
                iTravelGroupInfoService.update(new UpdateWrapper<TravelGroupInfo>()
                        .lambda()
                        .eq(TravelGroupInfo::getId, complaint.getTargetId())
                        .set(TravelGroupInfo::getStatus, GroupStatus.DISABLE.code())
                );
            }
        }

        return Result.success();
    }
}




