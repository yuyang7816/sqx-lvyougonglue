package com.sqx.app.lygl.shop.shopInfo.controller;

import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopInfoBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopPageBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopSaveBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopUpdateBo;
import com.sqx.app.lygl.shop.shopInfo.service.TravelShopService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Tag(name = "管理端-商户信息")
@RestController
@RequestMapping("admin/shop")
public class AdminShopController {

    @Resource
    private TravelShopService shopService;

    /**
     * 保存
     */
    @PostMapping("save")
    public Result<?> save(@RequestBody ShopSaveBo shopSaveBo) {
        return shopService.saveInfo(shopSaveBo);
    }

    /**
     * 列表
     */
    @PostMapping("page")
    public Result<?> page(@RequestBody ShopPageBo shopPageBo) {
        return shopService.pageInfo(shopPageBo);
    }

    /**
     * 信息
     */
    @PostMapping("info")
    public Result<?> info(@RequestBody ShopInfoBo shopInfoBo) {
        return shopService.info(shopInfoBo);
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result<?> update(@RequestBody ShopUpdateBo shopUpdateBo) {
        return shopService.updateInfo(shopUpdateBo);
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ShopInfoBo shopInfoBo) {
        return shopService.delete(shopInfoBo);
    }
}
