package com.sqx.app.lygl.shop.shopType.service;

import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeInfoBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypePageBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeSaveBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeUpdateBo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_shop_type(商户类型)】的数据库操作Service
* @createDate 2024-10-12 16:02:08
*/
public interface TravelShopTypeService {

    Result<?> saveInfo(ShopTypeSaveBo shopTypeSaveBo);

    Result<?> pageInfo(ShopTypePageBo shopTypePageBo);

    Result<?> info(ShopTypeInfoBo shopTypeInfoBo);

    Result<?> updateInfo(ShopTypeUpdateBo shopTypeUpdateBo);

    Result<?> delete(ShopTypeInfoBo shopTypeInfoBo);
}
