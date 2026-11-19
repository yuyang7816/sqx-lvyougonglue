package com.sqx.domain.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.domain.db.entity.MessageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author chengming
 * @since 2025/5/19
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {
    void sendUserSystemNotice(@Param("content") String content);
}
