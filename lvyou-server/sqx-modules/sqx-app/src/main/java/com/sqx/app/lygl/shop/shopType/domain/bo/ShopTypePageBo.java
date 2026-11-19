package com.sqx.app.lygl.shop.shopType.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopTypePageBo extends PageParam {

    /**
     * 商户类型名称
     */
    @Schema(description = "商户类型名称")
    private String name;
}
