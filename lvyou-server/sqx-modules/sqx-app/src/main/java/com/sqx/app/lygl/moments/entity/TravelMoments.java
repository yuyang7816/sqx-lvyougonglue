package com.sqx.app.lygl.moments.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 动态
 * @TableName travel_moments
 */
@TableName(value ="travel_moments")
@Data
public class TravelMoments implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 图片 逗号分隔
     */
    @TableField(value = "img")
    private String img;

    /**
     * 视频
     */
    @Schema(description = "视频")
    private String videoUrl;

    /**
     * 位置
     */
    @TableField(value = "location")
    private String location;

    /**
     * 景点id
     */
    @TableField(value = "scenic_spot_id")
    private Long scenicSpotId;

    /**
     * 浏览次数
     */
    @TableField(value = "views")
    private Integer views;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "纬度")
    private Double longitude;

    @Schema(description = "经度")
    private Double latitude;

    @Schema(description = "审核状态：0-待审核, 1-审核通过, 2-审核拒绝")
    @TableField("audit_status")
    private Integer auditStatus;

    @Schema(description = "审核理由")
    @TableField("audit_reason")
    private String auditReason;

    @Schema(description = "商户id")
    private Long shopId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "是否点赞")
    @TableField(exist = false)
    private Boolean isLike;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}