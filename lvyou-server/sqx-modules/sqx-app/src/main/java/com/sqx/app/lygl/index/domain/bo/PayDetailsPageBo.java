package com.sqx.app.lygl.index.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2025/11/6
 */
@Data
public class PayDetailsPageBo extends PageParam {

    @Schema(description = "0待支付 1支付成功 2失败")
    private Integer state;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
