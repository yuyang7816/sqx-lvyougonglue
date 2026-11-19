package com.sqx.app.lygl.group.groupInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupInfoBo {

    private Long currUserId;

    @NotNull(message = "id不能为空")
    @Schema(description = "唯一标识符，必须填写", required = true)
    private Long id;

    /**
     * 支付平台 1微信 2支付宝 3paypal 4stripe 5余额
     */
    @Schema(description = "支付平台类型：1-微信, 2-支付宝, 3-PayPal, 4-Stripe, 5-余额")
    private Integer platform;

    /**
     * 支付类型 应用：APP 网页：H5 小程序：JSAPI
     */
    @Schema(description = "支付类型: 'APP'-应用, 'H5'-网页, 'JSAPI'-小程序")
    private String tradeType;
}
