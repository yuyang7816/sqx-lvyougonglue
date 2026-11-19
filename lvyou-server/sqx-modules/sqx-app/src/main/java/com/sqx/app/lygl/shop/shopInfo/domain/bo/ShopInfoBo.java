package com.sqx.app.lygl.shop.shopInfo.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class ShopInfoBo {

    @Schema(description = "id")
    private Long id;

    private Long userId;
}
