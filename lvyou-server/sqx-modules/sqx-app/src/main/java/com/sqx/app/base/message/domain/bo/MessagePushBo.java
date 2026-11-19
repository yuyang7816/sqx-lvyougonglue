package com.sqx.app.base.message.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author chengming
 * @since 2025/11/25
 */
@Data
public class MessagePushBo {

    @Schema(description = "1=全部推送 2=部分推送")
    private Integer type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "手机号")
    private List<String> phones;
}
