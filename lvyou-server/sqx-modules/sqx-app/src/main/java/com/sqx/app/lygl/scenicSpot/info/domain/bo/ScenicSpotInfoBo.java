package com.sqx.app.lygl.scenicSpot.info.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class ScenicSpotInfoBo {

    @Schema(description = "id")
    private Long id;

    private Long userId;
}
