package com.sqx.app.lygl.index.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author chengming
 * @since 2026/3/19
 */
@Data
public class LineStatisticsBO {

    @Schema(description = "起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @Schema(description = "截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
