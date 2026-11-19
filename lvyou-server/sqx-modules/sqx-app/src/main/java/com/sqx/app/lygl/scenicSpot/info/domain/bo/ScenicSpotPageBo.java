package com.sqx.app.lygl.scenicSpot.info.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicSpotPageBo extends PageParam {

    /**
     * 景点名称
     */
    @Schema(description = "景点名称")
    private String name;

    /**
     * 景点位置
     */
    @Schema(description = "景点位置")
    private String city;

    private Long userId;
}
