package com.sqx.app.lygl.payDetails.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayDetails {

    // 充值id
    @Schema(description = "充值id")
    private Long id;

    // 用户id
    @Schema(description = "用户id")
    private Long userId;

    // 支付平台 1微信 2支付宝 3paypal 4stripe 5余额
    @Schema(description = "支付平台: 1-微信, 2-支付宝, 3-PayPal, 4-Stripe, 5-余额")
    private Integer platform;

    // 支付类型 应用：APP 网页：WEB 小程序：JSAPI
    @Schema(description = "支付类型: APP-应用, WEB-网页, JSAPI-小程序")
    private String tradeType;

    // 订单编号
    @Schema(description = "订单编号")
    private String orderNo;

    // 第三方平台支付流水号
    @Schema(description = "第三方平台支付流水号")
    private String tradeNo;

    // 金额
    @Schema(description = "金额")
    private BigDecimal amount;

    // 业务类型
    @Schema(description = "业务类型")
    private Integer businessType;

    // 0待支付 1支付成功 2失败
    @Schema(description = "支付状态: 0-待支付, 1-支付成功, 2-失败")
    private Integer state;

    // 支付时间
    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    // 备注
    @Schema(description = "备注")
    private String remark;

    // 创建时间
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 扩展字段
    @Schema(description = "扩展字段")
    private String extend;
}