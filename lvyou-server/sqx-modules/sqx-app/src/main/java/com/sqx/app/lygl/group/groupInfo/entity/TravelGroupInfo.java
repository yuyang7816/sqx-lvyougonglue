package com.sqx.app.lygl.group.groupInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 群信息表
 * @TableName travel_group_info
 */
@TableName(value ="travel_group_info")
@Data
public class TravelGroupInfo implements Serializable {
    /**
     * 群id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群所属用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 群类型id
     */
    @TableField(value = "type_id")
    private Long typeId;

    /**
     * 群名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 群费用金额
     */
    @TableField(value = "charge")
    private BigDecimal charge;

    /**
     * 群人数
     */
    @TableField(value = "people_count")
    private Integer peopleCount;

    /**
     * 群二维码
     */
    @TableField(value = "qr_code")
    private String qrCode;

    /**
     * 二维码开始日期
     */
    @TableField(value = "qr_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime qrStartTime;

    /**
     * 加入群聊有效天数
     */
    @TableField(value = "expired_days")
    private Integer expiredDays;

    /**
     * 群过期日期
     */
    @TableField(value = "expired_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;

    /**
     * 申请类型（0免费，1付费）
     */
    @TableField(value = "apply_condition")
    private Integer applyCondition;

    /**
     * 审核状态（0待审核 1审核通过 2审核拒绝）
     */
    @TableField(value = "audit_status")
    private Integer auditStatus;

    /**
     * 审核备注
     */
    @TableField(value = "audit_remark")
    private String auditRemark;

    /**
     * 群状态（0正常 1禁用 2过期 3人数已达最大）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 群扩展信息字段（json-设置地址 最大金额 最大人数 最低人数 最低金额）
     */
    @TableField(value = "extended_fields")
    private String extendedFields;

    /**
     * 群封面图
     */
    @TableField(value = "cover_img")
    private String coverImg;

    /**
     * 省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 群地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 群简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 群备注/详情，以逗号分隔
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 浏览次数
     */
    @TableField(value = "views")
    private Long views;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    @TableField(value = "create_by")
    private Integer createBy;

    /**
     * 修改人id
     */
    @TableField(value = "update_by")
    private Integer updateBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}