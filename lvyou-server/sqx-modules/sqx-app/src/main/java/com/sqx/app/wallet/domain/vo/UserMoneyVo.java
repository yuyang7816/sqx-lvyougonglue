package com.sqx.app.wallet.domain.vo;

import com.sqx.app.wallet.domain.entity.UserMoney;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/7/8
 */
@Data
public class UserMoneyVo extends UserMoney {

    @Schema(description = "累计充值金额")
    private BigDecimal totalRecharge;
}
