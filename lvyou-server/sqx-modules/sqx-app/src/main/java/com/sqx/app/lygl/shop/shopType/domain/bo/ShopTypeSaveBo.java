package com.sqx.app.lygl.shop.shopType.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class ShopTypeSaveBo {

    /**
     * 商户类型名称
     */
    @Schema(description = "商户类型名称")
    private String name;
}
