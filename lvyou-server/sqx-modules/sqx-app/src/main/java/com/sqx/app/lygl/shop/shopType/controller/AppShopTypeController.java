package com.sqx.app.lygl.shop.shopType.controller;

import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeInfoBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypePageBo;
import com.sqx.app.lygl.shop.shopType.service.TravelShopTypeService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 *
 * 用户端-商户类型
 */
@Tag(name = "用户端--商户类型")
@RestController
@RequestMapping("app/shopType")
public class AppShopTypeController {

    @Resource
    private TravelShopTypeService shopTypeService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody ShopTypePageBo shopTypePageBo) {
        return shopTypeService.pageInfo(shopTypePageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody ShopTypeInfoBo shopTypeInfoBo) {
        return shopTypeService.info(shopTypeInfoBo);
    }
}
