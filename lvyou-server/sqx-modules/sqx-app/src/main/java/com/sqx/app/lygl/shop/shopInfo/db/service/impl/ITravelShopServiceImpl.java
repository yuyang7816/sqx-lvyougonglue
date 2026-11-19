package com.sqx.app.lygl.shop.shopInfo.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.shop.shopInfo.db.mapper.TravelShopMapper;
import com.sqx.app.lygl.shop.shopInfo.db.service.ITravelShopService;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_shop(商户信息)】的数据库操作Service实现
* @createDate 2024-10-12 16:18:04
*/
@Service
public class ITravelShopServiceImpl extends ServiceImpl<TravelShopMapper, TravelShop>
    implements ITravelShopService {

    @Resource
    private TravelShopMapper travelShopMapper;

    @Override
    public TravelShopVo info(Long userId, Long shopId) {
        return travelShopMapper.info(userId, shopId);
    }

    @Override
    public IPage<TravelShopVo> searchShop(Page<TravelShopVo> initPage, Long userId, String keyword) {
        return travelShopMapper.searchShop(initPage, userId, keyword);
    }
}




