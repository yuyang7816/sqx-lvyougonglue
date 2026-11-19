package com.sqx.app.lygl.destination.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DestinationPageBo extends PageParam {

    @Schema(description = "城市")
    private String city;

    @Schema(description = "热门 1=是 2=否")
    private Integer popular;

    @Schema(description = "精选 1=是 2=否")
    private Integer choiceness;

    @Schema(description = "境外 1=是 2=否")
    private Integer overseas;
}
