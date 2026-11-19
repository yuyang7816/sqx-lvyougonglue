package com.sqx.app.lygl.scenicSpot.ticket.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Data
public class ScenicSpotTicketSaveBo {
    /**
     * 景点id
     */
    @Schema(description = "景点的唯一标识符")
    private Long scenicSpotId;

    /**
     * 起始时间
     */
    @Schema(description = "事件或活动的开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "事件或活动的结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 门票名称
     */
    @Schema(description = "门票的名称")
    private String name;

    /**
     * 门票介绍
     */
    @Schema(description = "关于门票的详细介绍")
    private String nameIntro;

    /**
     * 排序
     */
    @Schema(description = "用于排序的顺序值")
    private Integer sort;
}
