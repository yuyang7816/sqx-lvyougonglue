package com.sqx.app.lygl.shop.shopInfo.controller;

import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopInfoBo;
import com.sqx.app.lygl.shop.shopInfo.domain.bo.ShopPageBo;
import com.sqx.app.lygl.shop.shopInfo.service.TravelShopService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Tag(name = "用户端-商户信息")
@RestController
@RequestMapping("app/shop")
public class AppShopController {

    @Resource
    private TravelShopService shopService;

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
    @Login
    @PostMapping("info")
    public Result<?> info(@RequestAttribute Long userId, @RequestBody ShopInfoBo shopInfoBo) {
        shopInfoBo.setUserId(userId);
        return shopService.info(shopInfoBo);
    }
}
