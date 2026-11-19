package com.sqx.app.lygl.complaint.complaintInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Data
public class ComplaintUpdateBo {

    @Schema(description = "唯一标识符")
    private Long id;

    /**
     * 投诉用户
     */
    @Schema(description = "投诉用户的ID")
    private Long userId;

    /**
     * 目标id
     */
    @Schema(description = "目标ID，针对哪个用户或群聊进行投诉")
    private Long targetId;

    /**
     * 1用户 2群聊
     */
    @Schema(description = "投诉目标类型：1代表用户，2代表群聊")
    private Integer targetType;

    /**
     * 投诉原因
     */
    @Schema(description = "投诉原因的简短描述")
    private String reason;

    /**
     * 投诉说明
     */
    @Schema(description = "投诉的详细说明或额外信息")
    private String instruction;

    /**
     * 图片 以逗号分隔
     */
    @Schema(description = "与投诉相关的图片URL，多个URL以逗号分隔")
    private String imgUrl;
}
