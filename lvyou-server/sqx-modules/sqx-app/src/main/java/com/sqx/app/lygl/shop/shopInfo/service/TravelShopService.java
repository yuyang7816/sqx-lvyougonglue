package com.sqx.app.lygl.shop.shopInfo.service;

import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopInfoBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopPageBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopSaveBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopUpdateBo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_shop(商户信息)】的数据库操作Service
* @createDate 2024-10-12 16:18:04
*/
public interface TravelShopService  {

    Result<?> saveInfo(ShopSaveBo shopSaveBo);

    Result<?> pageInfo(ShopPageBo shopPageBo);

    Result<?> info(ShopInfoBo shopInfoBo);

    Result<?> updateInfo(ShopUpdateBo shopUpdateBo);

    Result<?> delete(ShopInfoBo shopInfoBo);

    void subCollect(Long dataId, Integer num);

    void addCollect(Long dataId, int num);
}
