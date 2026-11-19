package com.sqx.app.lygl.shop.shopType.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.shop.shopInfo.db.service.ITravelShopService;
import com.sqx.app.lygl.shop.shopType.db.mapper.TravelShopTypeMapper;
import com.sqx.app.lygl.shop.shopType.db.service.ITravelShopTypeService;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeInfoBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypePageBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeSaveBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeUpdateBo;
import com.sqx.app.lygl.shop.shopType.entity.TravelShopType;
import com.sqx.app.lygl.shop.shopType.service.TravelShopTypeService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【travel_shop_type(商户类型)】的数据库操作Service实现
* @createDate 2024-10-12 16:02:08
*/
@Service
public class TravelShopTypeServiceImpl implements TravelShopTypeService {

    @Resource
    private ITravelShopTypeService iTravelShopTypeService;

    @Override
    public Result<?> saveInfo(ShopTypeSaveBo shopTypeSaveBo) {
        TravelShopType shopType = new TravelShopType();
        BeanUtils.copyProperties(shopTypeSaveBo, shopType);

        iTravelShopTypeService.save(shopType);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(ShopTypePageBo shopTypePageBo) {
        Page<TravelShopType> initPage = PageUtil.initPage(shopTypePageBo.getPage(), shopTypePageBo.getLimit());

        QueryWrapper<TravelShopType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(shopTypePageBo.getName()), TravelShopType::getName, shopTypePageBo.getName());

        IPage<TravelShopType> shopTypeIPage = iTravelShopTypeService.page(initPage, queryWrapper);
        return Result.success(shopTypeIPage);
    }

    @Override
    public Result<?> info(ShopTypeInfoBo shopTypeInfoBo) {
        TravelShopType shopType = iTravelShopTypeService.getById(shopTypeInfoBo.getId());
        if (Objects.isNull(shopType)) {
            throw new SqxException("商户类型不存在");
        }

        return Result.success(shopType);
    }

    @Override
    public Result<?> updateInfo(ShopTypeUpdateBo shopTypeUpdateBo) {
        TravelShopType shopType = new TravelShopType();
        BeanUtils.copyProperties(shopTypeUpdateBo, shopType);

        iTravelShopTypeService.updateById(shopType);
        return Result.success();
    }

    @Override
    public Result<?> delete(ShopTypeInfoBo shopTypeInfoBo) {
        iTravelShopTypeService.removeById(shopTypeInfoBo.getId());
        return Result.success();
    }
}




