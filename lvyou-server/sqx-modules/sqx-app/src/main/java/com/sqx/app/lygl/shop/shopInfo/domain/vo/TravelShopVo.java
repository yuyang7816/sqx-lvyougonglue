package com.sqx.app.lygl.shop.shopInfo.domain.vo;


import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TravelShopVo extends TravelShop {

    /**
     * 商户类型名称
     */
    private String shopTypeName;

    @Schema(description = "是否收藏")
    private Boolean isCollect;
}
