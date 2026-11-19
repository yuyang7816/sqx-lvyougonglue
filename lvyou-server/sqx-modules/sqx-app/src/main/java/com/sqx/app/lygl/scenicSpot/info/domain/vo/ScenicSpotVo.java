package com.sqx.app.lygl.scenicSpot.info.domain.vo;

import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.app.lygl.scenicSpot.ticket.entity.TravelScenicSpotTicket;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicSpotVo extends TravelScenicSpot {

    @Schema(description = "收藏数")
    private Long collectNum;

    @Schema(description = "是否收藏 true=收藏 false=未收藏")
    private Boolean isCollect;

    @Schema(description = "门票")
    private List<TravelScenicSpotTicket> travelScenicSpotTickets;
}
