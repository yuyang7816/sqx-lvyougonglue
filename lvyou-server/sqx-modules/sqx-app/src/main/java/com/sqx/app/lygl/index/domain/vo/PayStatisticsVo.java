package com.sqx.app.lygl.index.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chengming
 * @since 2025/11/6
 */
@Data
public class PayStatisticsVo {

    @Schema(description = "数据列表")
    private List<DataInfo> dataInfoList;

    @Data
    public static class DataInfo {
        @Schema(description = "支付平台 1微信 2支付宝")
        private Integer platform;

        @Schema(description = "金额")
        private BigDecimal amount;
    }
}
