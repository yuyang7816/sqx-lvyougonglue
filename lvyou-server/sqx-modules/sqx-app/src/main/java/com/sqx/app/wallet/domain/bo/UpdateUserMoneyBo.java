package com.sqx.app.wallet.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/12/4
 */
@Data
public class UpdateUserMoneyBo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "操作 incr=增加 decr=减少")
    private String opt;
}
