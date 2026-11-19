package com.sqx.app.lygl.shop.shopInfo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.shop.shopInfo.db.mapper.TravelShopMapper;
import com.sqx.app.lygl.shop.shopInfo.db.service.ITravelShopService;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopInfoBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopPageBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopSaveBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopUpdateBo;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import com.sqx.app.lygl.shop.shopInfo.service.TravelShopService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【travel_shop(商户信息)】的数据库操作Service实现
* @createDate 2024-10-12 16:18:04
*/
@Service
public class TravelShopServiceImpl implements TravelShopService {

    @Resource
    private ITravelShopService iTravelShopService;
    @Resource
    private TravelShopMapper shopMapper;

    @Override
    public Result<?> saveInfo(ShopSaveBo shopSaveBo) {
        TravelShop shop = new TravelShop();
        BeanUtils.copyProperties(shopSaveBo, shop);

        shop.setCollectNum(0);
        iTravelShopService.save(shop);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ShopPageBo shopPageBo) {
        Page<TravelShop> initPage = PageUtil.initPage(shopPageBo.getPage(), shopPageBo.getLimit());

        IPage<TravelShopVo> shopVoIPage = shopMapper.pageInfo(initPage, shopPageBo);
        return Result.success(shopVoIPage);
    }

    @Override
    public Result<?> info(ShopInfoBo shopInfoBo) {
        TravelShopVo shop = iTravelShopService.info(shopInfoBo.getUserId(), shopInfoBo.getId());
        if (Objects.isNull(shop)) {
            throw new SqxException("商户信息不存在");
        }

        return Result.success(shop);
    }

    @Override
    public Result<?> updateInfo(ShopUpdateBo shopUpdateBo) {
        TravelShop shop = new TravelShop();
        BeanUtils.copyProperties(shopUpdateBo, shop);

        iTravelShopService.updateById(shop);
        return Result.success();
    }

    @Override
    public Result<?> delete(ShopInfoBo shopInfoBo) {
        iTravelShopService.removeById(shopInfoBo.getId());
        return Result.success();
    }

    @Override
    public void subCollect(Long dataId, Integer num) {
        shopMapper.subCollect(dataId, num);
    }

    @Override
    public void addCollect(Long dataId, int num) {
        shopMapper.addCollect(dataId, num);
    }
}




