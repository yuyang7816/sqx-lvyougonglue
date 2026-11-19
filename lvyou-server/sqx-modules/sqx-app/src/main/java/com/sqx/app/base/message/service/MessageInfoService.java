package com.sqx.app.base.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.base.message.domain.bo.*;
import com.sqx.app.base.message.domain.vo.MessageInfoVo;
import com.sqx.app.base.message.domain.vo.MessageListVo;
import com.sqx.common.core.domain.Result;

/**
 * @author chengming
 * @since 2025/5/19
 */
public interface MessageInfoService {
    Result<Void> saveInfo(MessageInfoSaveBo messageInfoSaveBo);

    Result<IPage<MessageInfoVo>> pageInfo(MessageInfoPageBo messageInfoPageBo);

    Result<MessageInfoVo> info(MessageInfoInfoBo messageInfoInfoBo);

    Result<Void> updateInfo(MessageInfoUpdateBo messageInfoUpdateBo);

    Result<Void> delete(MessageInfoInfoBo messageInfoInfoBo);

    Result<MessageListVo> messageList(Long userId);

    void push(MessagePushBo messagePushBo);
}
