package com.sqx.app.base.message.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/10/9
 */
@Data
public class MessageListVo {

    @Schema(description = "系统公告未读数")
    private Long systemNoticeUnreadCount;

    @Schema(description = "评论消息未读数")
    private Long commentUnreadCount;

    @Schema(description = "系统公告最后一条消息")
    private String systemNoticeLastContent;

    @Schema(description = "评论消息最后一条消息")
    private String commentLastContent;
}
