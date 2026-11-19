package com.sqx.app.lygl.group.groupInfo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alipay.api.domain.GroupInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.pay.enums.BusinessType;
import com.sqx.app.lygl.group.groupInfo.db.mapper.TravelGroupInfoMapper;
import com.sqx.app.lygl.group.groupInfo.db.service.ITravelGroupInfoService;
import com.sqx.app.lygl.group.groupInfo.domain.bo.*;
import com.sqx.app.lygl.group.groupInfo.domain.vo.GroupInfoVo;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import com.sqx.app.lygl.group.groupInfo.enums.GroupApplyCondition;
import com.sqx.app.lygl.group.groupInfo.enums.GroupAuditStatus;
import com.sqx.app.lygl.group.groupInfo.enums.GroupStatus;
import com.sqx.app.lygl.group.groupInfo.service.TravelGroupInfoService;
import com.sqx.app.lygl.group.groupType.db.service.ITravelGroupTypeService;
import com.sqx.app.lygl.group.groupType.entity.TravelGroupType;
import com.sqx.app.lygl.group.groupUser.db.service.ITravelGroupUserService;
import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import com.sqx.app.lygl.group.groupUser.enums.GroupUserStatus;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import com.sqx.app.lygl.payDetails.enums.PayDetailState;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.core.utils.GenUtil;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【travel_group_info(群信息表)】的数据库操作Service实现
 * @createDate 2024-10-12 10:32:58
 */
@Service
public class TravelGroupInfoServiceImpl implements TravelGroupInfoService {

    @Resource
    private ITravelGroupInfoService iTravelGroupInfoService;
    @Resource
    private TravelGroupInfoMapper groupInfoMapper;
    @Resource
    private ITravelGroupTypeService groupTypeService;
    @Resource
    private ITravelGroupUserService groupUserService;

    @Override
    public Result<?> saveInfo(GroupInfoSaveBo groupInfoSaveBo) {
        checkGroupSaveParam(groupInfoSaveBo);

        TravelGroupInfo groupInfo = new TravelGroupInfo();
        BeanUtils.copyProperties(groupInfoSaveBo, groupInfo);

        groupInfo.setAuditStatus(GroupAuditStatus.WAIT.code());
        groupInfo.setStatus(GroupStatus.DISABLE.code());

        groupInfo.setPeopleCount(1);
        groupInfo.setViews(0L);
        groupInfo.setExpiredDays(30);
        iTravelGroupInfoService.save(groupInfo);

        // 群主入群
        TravelGroupUser travelGroupUser = new TravelGroupUser();
        travelGroupUser.setUserId(groupInfoSaveBo.getUserId());
        travelGroupUser.setGroupInfoId(groupInfo.getId());
        travelGroupUser.setStatus(1);
        travelGroupUser.setExpireTime(groupInfoSaveBo.getExpiredTime());
        groupUserService.save(travelGroupUser);
        return Result.success();
    }

    /**
     * 校验群聊创建参数
     *
     * @param groupInfoSaveBo 参数
     */
    private void checkGroupSaveParam(GroupInfoSaveBo groupInfoSaveBo) {
        TravelGroupType groupType = groupTypeService.getById(groupInfoSaveBo.getTypeId());
        if (Objects.isNull(groupType)) {
            throw new SqxException("群聊类型不存在");
        }

        if (groupInfoSaveBo.getCharge().compareTo(groupType.getMaxAmount()) > 0) {
            throw new SqxException("群聊金额不能大于" + groupType.getMaxAmount());
        }

        if (groupInfoSaveBo.getCharge().compareTo(groupType.getMinAmount()) < 0) {
            throw new SqxException("群聊金额不能小于" + groupType.getMinAmount());
        }
    }

    /**
     * 创建群组二维码
     *
     * @param groupInfoId 群组id
     * @return
     */
    private String createQrCode(Long groupInfoId) {
        QrConfig config = new QrConfig(300, 300);

        String path = "qrCode/group" + groupInfoId + ".jpg";
        try {
            QrCodeUtil.generate(String.valueOf(groupInfoId), config, FileUtil.file(path));
        } catch (Exception e) {
            throw new SqxException("生成二维码失败");
        }

        return path;
    }

    @Override
    public Result<?> pageInfo(GroupInfoPageBo groupInfoPageBo) {
        Page<TravelGroupInfo> initPage = PageUtil.initPage(groupInfoPageBo.getPage(), groupInfoPageBo.getLimit());

        IPage<GroupInfoVo> groupInfoVoIPage = groupInfoMapper.pageInfo(initPage, groupInfoPageBo);

        if (CollectionUtils.isNotEmpty(groupInfoVoIPage.getRecords())) {
            // 是否加入群组
            List<Long> groupIds = groupInfoVoIPage.getRecords().stream()
                    .map(GroupInfoVo::getId)
                    .collect(Collectors.toList());

            Map<Long, Boolean> joinGroupMap = checkJoinGroup(groupInfoPageBo.getCurrUserId(), groupIds);

            // 群组人数
            Map<Long, Long> groupUserMap = countGroupUserByGroupIds(groupIds);

            for (GroupInfoVo vo : groupInfoVoIPage.getRecords()) {
                vo.setIsJoinGroup(joinGroupMap.get(vo.getId()));

                vo.setJoinNum(groupUserMap.getOrDefault(vo.getId(), 0L));
            }
        }

        return Result.success(groupInfoVoIPage);
    }

    private Map<Long, Long> countGroupUserByGroupIds(List<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyMap();
        }

        LambdaQueryWrapper<TravelGroupUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TravelGroupUser::getGroupInfoId, groupIds);

        List<TravelGroupUser> groupUserList = groupUserService.list(queryWrapper);

        Map<Long, Long> map = new HashMap<>();
        for (TravelGroupUser travelGroupUser : groupUserList) {
            map.merge(travelGroupUser.getGroupInfoId(), 1L, Long::sum);
        }

        return map;
    }

    @Override
    public Result<?> info(GroupInfoBo groupInfoBo) {
        GroupInfoVo vo = iTravelGroupInfoService.getInfo(groupInfoBo.getId());
        if (Objects.isNull(vo)) {
            throw new SqxException("群聊信息不存在");
        }

        vo.setIsJoinGroup(checkJoinGroup(groupInfoBo.getCurrUserId(), vo.getId()));
        vo.setDays(ChronoUnit.DAYS.between(vo.getCreateTime(), LocalDateTime.now()));
        vo.setJoinNum(countGroupUser(vo.getId()));

        // 群聊浏览次数+1
        groupInfoMapper.addViews(vo.getId(), 1);
        return Result.success(vo);
    }

    private Long countGroupUser(Long groupId) {
        LambdaQueryWrapper<TravelGroupUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TravelGroupUser::getGroupInfoId, groupId);

        return groupUserService.count(queryWrapper);
    }

    private Boolean checkJoinGroup(Long currUserId, Long groupId) {
        LambdaQueryWrapper<TravelGroupUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TravelGroupUser::getGroupInfoId, groupId)
                .eq(TravelGroupUser::getUserId, currUserId);

        return groupUserService.exists(queryWrapper);
    }

    private Map<Long, Boolean> checkJoinGroup(Long currUserId, List<Long> groupIds) {
        Map<Long, Boolean> map = new HashMap<>();

        LambdaQueryWrapper<TravelGroupUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(TravelGroupUser::getGroupInfoId, groupIds)
                .eq(TravelGroupUser::getUserId, currUserId);

        List<TravelGroupUser> groupUserList = groupUserService.list(queryWrapper);
        List<Long> joinGroupIds = groupUserList.stream()
                .map(TravelGroupUser::getGroupInfoId)
                .collect(Collectors.toList());

        for (Long groupId : groupIds) {
            map.put(groupId, joinGroupIds.contains(groupId));
        }

        return map;
    }

    @Override
    public Result<?> updateInfo(GroupInfoUpdateBo groupInfoUpdateBo) {
        TravelGroupInfo groupInfo = new TravelGroupInfo();
        BeanUtils.copyProperties(groupInfoUpdateBo, groupInfo);

        iTravelGroupInfoService.updateById(groupInfo);
        return Result.success();
    }

    @Override
    public Result<?> delete(GroupInfoBo groupInfoBo) {
        iTravelGroupInfoService.removeById(groupInfoBo.getId());
        return Result.success();
    }

    @Override
    public Result<?> join(Long userId, GroupInfoBo groupInfoBo) {
        // 群组是否还存在
        TravelGroupInfo groupInfo = iTravelGroupInfoService.getById(groupInfoBo.getId());
        if (Objects.isNull(groupInfo)) {
            throw new SqxException("群聊已解散");
        }

        // 用户是否已经加入群组
        boolean exists = groupUserService.exists(new QueryWrapper<TravelGroupUser>()
                .lambda()
                .eq(TravelGroupUser::getGroupInfoId, groupInfoBo.getId())
                .eq(TravelGroupUser::getUserId, userId)
                .eq(TravelGroupUser::getStatus, GroupUserStatus.ENABLE.code())
        );
        if (exists) {
            throw new SqxException("您已加入该群聊");
        }

        // 免费的直接加入
        TravelGroupUser groupUser = new TravelGroupUser();
        groupUser.setUserId(userId);
        groupUser.setGroupInfoId(groupInfo.getId());
        groupUser.setStatus(GroupUserStatus.ENABLE.code());
        groupUser.setExpireTime(LocalDateTime.MAX);

        groupUserService.save(groupUser);

        // 群聊人数+1
        groupInfoMapper.addPeople(groupInfo.getId(), 1);
        return Result.success();
    }

    @Override
    public Result<?> updateStatus(GroupInfoUpdateStatusBo groupInfoUpdateStatusBo) {
        UpdateWrapper<TravelGroupInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelGroupInfo::getId, groupInfoUpdateStatusBo.getId())
                .set(TravelGroupInfo::getStatus, groupInfoUpdateStatusBo.getStatus());

        iTravelGroupInfoService.update(new TravelGroupInfo(), updateWrapper);
        return Result.success();
    }

    @Override
    public Result<?> examine(GroupInfoExamineBo groupInfoExamineBo) {
        UpdateWrapper<TravelGroupInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelGroupInfo::getId, groupInfoExamineBo.getId())
                .set(TravelGroupInfo::getAuditStatus, groupInfoExamineBo.getAuditStatus())
                .set(TravelGroupInfo::getAuditRemark, groupInfoExamineBo.getAuditRemark());

        if (Objects.equals(groupInfoExamineBo.getAuditStatus(), GroupAuditStatus.PASS.code())) {
            updateWrapper.lambda().set(TravelGroupInfo::getStatus, GroupStatus.ENABLE.code());
        }

        iTravelGroupInfoService.update(new TravelGroupInfo(), updateWrapper);
        return Result.success();
    }

    @Override
    public Result<?> owner(GroupOwnerBo groupOwnerBo) {
        Page<TravelGroupInfo> initPage = PageUtil.initPage(groupOwnerBo.getPage(), groupOwnerBo.getLimit());

        IPage<GroupInfoVo> voIPage = iTravelGroupInfoService.owner(initPage, groupOwnerBo);

        if (CollectionUtils.isNotEmpty(voIPage.getRecords())) {
            // 是否加入群组
            List<Long> groupIds = voIPage.getRecords().stream()
                    .map(GroupInfoVo::getId)
                    .collect(Collectors.toList());

            // 群组人数
            Map<Long, Long> groupUserMap = countGroupUserByGroupIds(groupIds);

            for (GroupInfoVo vo : voIPage.getRecords()) {
                vo.setJoinNum(groupUserMap.getOrDefault(vo.getId(), 0L));
            }
        }

        return Result.success(voIPage);
    }

    @Override
    public Result<?> joinList(GroupOwnerBo groupOwnerBo) {
        Page<TravelGroupInfo> initPage = PageUtil.initPage(groupOwnerBo.getPage(), groupOwnerBo.getLimit());

        IPage<GroupInfoVo> voIPage = iTravelGroupInfoService.joinList(initPage, groupOwnerBo);

        if (CollectionUtils.isNotEmpty(voIPage.getRecords())) {
            // 是否加入群组
            List<Long> groupIds = voIPage.getRecords().stream()
                    .map(GroupInfoVo::getId)
                    .collect(Collectors.toList());

            // 群组人数
            Map<Long, Long> groupUserMap = countGroupUserByGroupIds(groupIds);

            for (GroupInfoVo vo : voIPage.getRecords()) {
                vo.setJoinNum(groupUserMap.getOrDefault(vo.getId(), 0L));
            }
        }

        return Result.success(voIPage);
    }

    @Override
    public Result<?> enable(GroupEnableBo groupEnableBo) {
        LambdaQueryWrapper<TravelGroupInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TravelGroupInfo::getId, groupEnableBo.getGroupId())
                .eq(TravelGroupInfo::getUserId, groupEnableBo.getUserId());

        TravelGroupInfo travelGroupInfo = iTravelGroupInfoService.getOne(queryWrapper);
        if (Objects.isNull(travelGroupInfo)) {
            throw new BizException("不能修改此群状态");
        }

        if (Objects.equals(travelGroupInfo.getStatus(), GroupStatus.ENABLE.code())) {
            travelGroupInfo.setStatus(GroupStatus.DISABLE.code());
        } else if (Objects.equals(travelGroupInfo.getStatus(), GroupStatus.DISABLE.code())) {
            travelGroupInfo.setStatus(GroupStatus.ENABLE.code());
        }

        iTravelGroupInfoService.updateById(travelGroupInfo);
        return Result.success();
    }
}




