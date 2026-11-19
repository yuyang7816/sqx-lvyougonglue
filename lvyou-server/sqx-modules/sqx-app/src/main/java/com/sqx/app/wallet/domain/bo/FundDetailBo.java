package com.sqx.app.wallet.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2025/6/20
 */
@Data
public class FundDetailBo extends PageParam {
    /**
     * 渠道 1注册 2购买 3提现
     */
    @Schema(description = "1注册  2购买 3提现")
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

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "起始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

}
