package com.sqx.app.base.pay.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author chengming
 * @since 2025/4/22
 *
 * 业务参数
 */
@Data
public class BusinessParam {

    @NotBlank(message = "业务类型不能为空")
    @Schema(name = "businessType", description = "业务类型 1会员 2付费群组")
    private String businessType;

    @NotBlank(message = "支付成功后重定向uri不能为空")
    @Schema(name = "redirectUri", description = "支付成功后重定向uri")
    private String redirectUri;

    @NotNull(message = "订单金额不能为空")
    @Schema(name = "amount", description = "订单金额")
    private BigDecimal amount;

    @Schema(description = "微信支付参数（微信支付时必填）")
    private WxPayParam wxPayParam;

    @Schema(description = "支付宝支付参数（支付宝支付时必填）")
    private AliPayParam aliPayParam;

    private String orderNo;

    private Long userId;

    /* 其他业务参数 */
    @Schema(description = "其他业务参数")
    private Map<String, Object> extensions;
}
