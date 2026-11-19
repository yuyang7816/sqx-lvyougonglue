package com.sqx.app.lygl.scenicSpot.ticket.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicSpotTicketPageBo extends PageParam {

    /**
     * 景点门票
     */
    @Schema(description = "景点门票")
    private Long scenicSpotId;
}
