package com.sqx.domain.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.db.entity.MessageInfo;
import com.sqx.domain.db.mapper.MessageInfoMapper;
import com.sqx.domain.db.service.IMessageInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现类
 *
 * @author sqx
 * @since 2025-05-19 15:35:07
 */
@Service
public class IMessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    @Resource
    private MessageInfoMapper messageInfoMapper;

    @Override
    public void saveComment(Long userId, String content, Long dataId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setTitle("评论消息");
        messageInfo.setContent(content);
        messageInfo.setUserId(userId);
        messageInfo.setIsSee(2);
        messageInfo.setType(2);
        messageInfo.setDataId(dataId);

        this.save(messageInfo);
    }

    @Override
    public void sendUserSystemNotice(String content) {
        messageInfoMapper.sendUserSystemNotice(content);
    }
}
