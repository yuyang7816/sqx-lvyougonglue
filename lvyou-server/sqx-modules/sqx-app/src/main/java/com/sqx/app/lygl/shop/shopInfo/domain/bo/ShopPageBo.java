package com.sqx.app.lygl.shop.shopInfo.domain.bo;

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
public class ShopPageBo extends PageParam {

    /**
     * 商户类型id
     */
    @Schema(description = "商户类型的唯一标识符")
    private Long typeId;

    /**
     * 商户类型名称
     */
    @Schema(description = "商户类型的名称")
    private String typeName;

    /**
     * 商户名称
     */
    @Schema(description = "商户的名称")
    private String name;

    /**
     * 省
     */
    @Schema(description = "商户所在的省份")
    private String province;

    /**
     * 市
     */
    @Schema(description = "商户所在的城市")
    private String city;

    /**
     * 区
     */
    @Schema(description = "商户所在的区域")
    private String region;

    /**
     * 联系方式
     */
    @Schema(description = "联系人的电话号码")
    private String phone;

    /**
     * 联系人姓名
     */
    @Schema(description = "联系人的姓名")
    private String contactName;
}
