package com.sqx.app.wallet.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_money_details")
@Schema(description = "钱包详情")
public class UserMoneyDetails implements Serializable {
    /**
     * 钱包详情id
     */
    @Schema(description = "钱包详情id")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    @Schema(description = "用户id")
    private Long userId;
    /**
     * 对应用户id
     */
    @TableField("by_user_id")
    @Schema(description = "邀请用户id")
    private Long byUserId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 用途 1购买会员 2支付订单 3充值余额 4提现
     */
    @Schema(description = "用途 1购买会员 2解锁群组")
    private Integer classify;

    /**
     * 类型1.收入 2.支出
     */
    @Schema(description = "类型1.收入 2.支出")
    private Integer type;

    /**
     * 状态 1待支付 2已到账 3取消
     */
    @Schema(description = "状态 1待支付 2已到账 3取消")
    private Integer state;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal money;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 关联id
     */
    @Schema(description = "关联id")
    private Long relationId;
}
