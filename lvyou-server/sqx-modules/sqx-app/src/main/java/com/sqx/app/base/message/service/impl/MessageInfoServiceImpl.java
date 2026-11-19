package com.sqx.app.base.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.message.domain.bo.*;
import com.sqx.app.base.message.domain.vo.MessageInfoVo;
import com.sqx.app.base.message.domain.vo.MessageListVo;
import com.sqx.app.base.message.service.MessageInfoService;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import com.sqx.domain.db.entity.MessageInfo;
import com.sqx.domain.db.entity.UserEntity;
import com.sqx.domain.db.service.IMessageInfoService;
import com.sqx.domain.db.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/5/19
 */
@Service
public class MessageInfoServiceImpl implements MessageInfoService {

    @Resource
    private IMessageInfoService iMessageInfoService;
    @Resource
    private ITravelCommentService iTravelCommentService;
    @Resource
    private IUserService iUserService;

    @Override
    public Result<Void> saveInfo(MessageInfoSaveBo messageInfoSaveBo) {
        MessageInfo messageInfo = new MessageInfo();
        BeanUtils.copyProperties(messageInfoSaveBo, messageInfo);

        if (1 == messageInfoSaveBo.getType()) {
            sendUserSystemNotice(messageInfoSaveBo);
        } else {
            iMessageInfoService.save(messageInfo);
        }

        return Result.success();
    }

    private void sendUserSystemNotice(MessageInfoSaveBo messageInfoSaveBo) {
        iMessageInfoService.sendUserSystemNotice(messageInfoSaveBo.getContent());
    }

    @Override
    public Result<IPage<MessageInfoVo>> pageInfo(MessageInfoPageBo messageInfoPageBo) {
        Page<MessageInfo> initPage = PageUtil.initPage(messageInfoPageBo.getPage(), messageInfoPageBo.getLimit());

        QueryWrapper<MessageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(MessageInfo::getUserId, 0L)
                .or(Objects.nonNull(messageInfoPageBo.getUserId()), wrapper -> wrapper.eq(MessageInfo::getUserId, messageInfoPageBo.getUserId()))
                .eq(Objects.nonNull(messageInfoPageBo.getType()), MessageInfo::getType, messageInfoPageBo.getType())
                .orderByDesc(MessageInfo::getCreateTime);

        IPage<MessageInfo> iPage = iMessageInfoService.page(initPage, queryWrapper);
        List<MessageInfoVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (MessageInfo record : iPage.getRecords()) {
                MessageInfoVo vo = new MessageInfoVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        // 修改消息为已查看
        LambdaUpdateWrapper<MessageInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(MessageInfo::getUserId, messageInfoPageBo.getUserId())
                .eq(MessageInfo::getType, messageInfoPageBo.getType())
                .set(MessageInfo::getIsSee, 1);
        iMessageInfoService.update(new MessageInfo(), updateWrapper);

        IPage<MessageInfoVo> voIPage = PageUtil.copyPage(iPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result<MessageInfoVo> info(MessageInfoInfoBo messageInfoInfoBo) {
        MessageInfo messageInfo = iMessageInfoService.getById(messageInfoInfoBo.getId());
        if (Objects.isNull(messageInfo)) {
            throw new SqxException("信息不存在");
        }

        MessageInfoVo vo = new MessageInfoVo();
        BeanUtils.copyProperties(messageInfo, vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> updateInfo(MessageInfoUpdateBo messageInfoUpdateBo) {
        MessageInfo messageInfo = new MessageInfo();
        BeanUtils.copyProperties(messageInfoUpdateBo, messageInfo);

        iMessageInfoService.updateById(messageInfo);
        return Result.success();
    }

    @Override
    public Result<Void> delete(MessageInfoInfoBo messageInfoInfoBo) {
        iMessageInfoService.removeById(messageInfoInfoBo.getId());
        return Result.success();
    }

    @Override
    public Result<MessageListVo> messageList(Long userId) {
        // 系统消息未读数
        Long systemNoticeUnreadCount = countSystemNoticeUnread(userId);

        // 评论消息未读数
        Long commentUnreadCount = countCommentUnread(userId);

        // 最后一条系统消息
        String lastSystemNotice = getLastSystemNotice(userId);

        // 最后一条评论消息
        String lastComment = getLastComment(userId);

        MessageListVo vo = new MessageListVo();
        vo.setSystemNoticeUnreadCount(systemNoticeUnreadCount);
        vo.setCommentUnreadCount(commentUnreadCount);
        vo.setCommentLastContent(lastComment);
        vo.setSystemNoticeLastContent(lastSystemNotice);

        return Result.success(vo);
    }

    @Override
    public void push(MessagePushBo messagePushBo) {
        if (1 == messagePushBo.getType()) {
            List<UserEntity> userEntityList = iUserService.list();

            List<MessageInfo> messageInfos = new ArrayList<>();
            for (UserEntity userEntity : userEntityList) {
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(userEntity.getUserId());
                messageInfo.setType(1);
                messageInfo.setTitle(messagePushBo.getTitle());
                messageInfo.setContent(messagePushBo.getContent());

                messageInfos.add(messageInfo);
            }

            iMessageInfoService.saveBatch(messageInfos);
            return;
        }

        if (2 == messagePushBo.getType()) {
            List<String> phones = messagePushBo.getPhones();

            for (String phone : phones) {
                UserEntity userEntity = iUserService.getByPhone(phone);
                if (Objects.isNull(userEntity)) {
                    continue;
                }

                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(userEntity.getUserId());
                messageInfo.setType(1);
                messageInfo.setTitle(messagePushBo.getTitle());
                messageInfo.setContent(messagePushBo.getContent());
                iMessageInfoService.save(messageInfo);
            }
        }
    }

    private String getLastComment(Long userId) {
        LambdaQueryWrapper<MessageInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageInfo::getUserId, userId)
                .eq(MessageInfo::getType, 2)
                .orderByDesc(MessageInfo::getCreateTime)
                .last("limit 1");

        MessageInfo messageInfo = iMessageInfoService.getOne(queryWrapper);
        if (Objects.isNull(messageInfo)) {
            return "";
        }

        TravelComment travelComment = iTravelCommentService.getById(messageInfo.getDataId());
        if (Objects.isNull(travelComment)) {
            return "";
        }

        return travelComment.getContent();
    }

    private String getLastSystemNotice(Long userId) {
        LambdaQueryWrapper<MessageInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageInfo::getUserId, userId)
                .eq(MessageInfo::getType, 1)
                .orderByDesc(MessageInfo::getCreateTime)
                .last("limit 1");

        MessageInfo messageInfo = iMessageInfoService.getOne(queryWrapper);
        if (Objects.isNull(messageInfo)) {
            return "";
        }

        return messageInfo.getContent();
    }

    private Long countCommentUnread(Long userId) {
        LambdaQueryWrapper<MessageInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageInfo::getUserId, userId)
                .eq(MessageInfo::getIsSee, 2)
                .eq(MessageInfo::getType, 2);

        return iMessageInfoService.count(queryWrapper);
    }

    private Long countSystemNoticeUnread(Long userId) {
        LambdaQueryWrapper<MessageInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MessageInfo::getUserId, userId)
                .eq(MessageInfo::getIsSee, 2)
                .eq(MessageInfo::getType, 1);

        return iMessageInfoService.count(queryWrapper);
    }
}
