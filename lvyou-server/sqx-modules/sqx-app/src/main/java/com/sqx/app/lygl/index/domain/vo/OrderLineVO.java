package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author chengming
 * @since 2026/3/19
 */
@Data
public class OrderLineVO {

    @Schema(description = "日期")
    private List<String> xDates;

    @Schema(description = "总订单数")
    private List<Long> totals;

    @Schema(description = "待付款订单数")
    private List<Long> waitCounts;

    @Schema(description = "已付款订单数")
    private List<Long> payedCounts;

    @Schema(description = "已发货订单数")
    private List<Long> shippedCounts;

    @Schema(description = "待发货订单数")
    private List<Long> receivedCounts;

    @Schema(description = "取消订单数")
    private List<Long> cancelCounts;

    @Schema(description = "退款中订单数")
    private List<Long> beingRefundCounts;

    @Schema(description = "拒绝退款订单数")
    private List<Long> refuseRefundCounts;

    @Schema(description = "已退款订单数")
    private List<Long> refundedCounts;

    @Schema(description = "拼团中订单数")
    private List<Long> groupingCounts;

    @Schema(description = "评价订单数")
    private List<Long> evaluatedCounts;

    @Schema(description = "已核销订单数")
    private List<Long> verifyCounts;

    @Schema(description = "超时订单数")
    private List<Long> timeoutCounts;
}
