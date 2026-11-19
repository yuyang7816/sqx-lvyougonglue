package com.sqx.app.lygl.shop.shopInfo.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;


/**
* @author Administrator
* @description 针对表【travel_shop(商户信息)】的数据库操作Service
* @createDate 2024-10-12 16:18:04
*/
public interface ITravelShopService extends IService<TravelShop> {

    TravelShopVo info(Long userId, Long shopId);

    IPage<TravelShopVo> searchShop(Page<TravelShopVo> initPage, Long userId, String keyword);
}
