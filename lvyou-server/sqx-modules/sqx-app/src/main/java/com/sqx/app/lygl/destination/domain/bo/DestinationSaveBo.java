package com.sqx.app.lygl.destination.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
@Data
public class DestinationSaveBo {

    @Schema(description = "城市")
    private String city;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "热门 1=是 2=否")
    private Integer popular;

    @Schema(description = "精选 1=是 2=否")
    private Integer choiceness;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

}
