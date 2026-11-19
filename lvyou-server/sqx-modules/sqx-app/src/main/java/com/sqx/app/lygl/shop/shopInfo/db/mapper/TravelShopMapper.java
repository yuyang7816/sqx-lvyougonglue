package com.sqx.app.lygl.shop.shopInfo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopPageBo;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_shop(商户信息)】的数据库操作Mapper
* @createDate 2024-10-12 16:18:04
* @Entity com.sqx.lvyougonglve.shopInfo.entity.TravelShop
*/
public interface TravelShopMapper extends BaseMapper<TravelShop> {

    IPage<TravelShopVo> pageInfo(Page<TravelShop> initPage, @Param("param") ShopPageBo shopPageBo);

    /**
     * 减少商户收藏数
     * @param dataId 商户ID
     */
    void subCollect(@Param("dataId") Long dataId, @Param("num") Integer num);

    /**
     * 增加商户收藏数
     * @param dataId 商户ID
     */
    void addCollect(@Param("dataId") Long dataId, @Param("num") Integer num);

    TravelShopVo info(@Param("userId") Long userId, @Param("shopId") Long shopId);

    IPage<TravelShopVo> searchShop(Page<TravelShopVo> initPage, @Param("userId") Long userId, @Param("keyword") String keyword);
}




