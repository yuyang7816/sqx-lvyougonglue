package com.sqx.app.lygl.group.groupInfo.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupInfoSaveBo {
    /**
     * 群所属用户id
     */
    @Schema(description = "群所属用户的ID")
    private Long userId;

    /**
     * 群类型id
     */
    @Schema(description = "群类型的ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "群类型不能为空")
    private Long typeId;

    /**
     * 群名称
     */
    @Schema(description = "群聊的名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请输入群聊名称")
    private String name;

    /**
     * 群费用金额
     */
    @Schema(description = "加入群聊需要支付的费用金额")
    private BigDecimal charge;

    /**
     * 群人数
     */
    @Schema(description = "当前群聊中的人数")
    private Integer peopleCount;

    /**
     * 加入群聊有效天数
     */
    @Schema(description = "加入群聊的有效天数")
    private Integer expiredDays;

    /**
     * 群过期时间
     */
    @Schema(description = "群聊的过期时间", type = "string", format = "date-time", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;

    /**
     * 申请类型（0免费，1付费）
     */
    @Schema(description = "申请类型: 0-免费, 1-付费", allowableValues = {"0", "1"}, required = true)
    @NotNull(message = "请选择申请类型")
    private Integer applyCondition;

    /**
     * 群扩展信息字段（json-设置地址 最大金额 最大人数 最低人数 最低金额）
     */
    @Schema(description = "群聊的扩展信息字段，以JSON格式存储：包括但不限于设置地址、最大金额、最大人数、最低人数、最低金额")
    private String extendedFields;

    /**
     * 群封面图
     */
    @Schema(description = "群聊封面图片的URL")
    private String coverImg;

    /**
     * 省
     */
    @Schema(description = "群聊所在省份")
    private String province;

    /**
     * 市
     */
    @Schema(description = "群聊所在城市")
    private String city;

    /**
     * 区
     */
    @Schema(description = "群聊所在区县")
    private String region;

    /**
     * 群地址
     */
    @Schema(description = "详细群聊地址")
    private String address;

    /**
     * 群简介
     */
    @Schema(description = "群聊的简短介绍")
    private String description;

    /**
     * 群备注/详情，以逗号分隔
     */
    @Schema(description = "群聊的备注或更多详情信息，以逗号分隔")
    private String remark;

    @Schema(description = "二维码开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime qrStartTime;
}
