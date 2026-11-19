package com.sqx.app.lygl.shop.shopType.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeInfoBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypePageBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeSaveBo;
import com.sqx.app.lygl.shop.shopType.domain.bo.ShopTypeUpdateBo;
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
 * <p>
 * 管理端-商户类型
 */
@Tag(name = "管理端-商户类型")
@RestController
@RequestMapping("admin/shopType")
public class AdminShopTypeController {

    @Resource
    private TravelShopTypeService shopTypeService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody ShopTypeSaveBo shopTypeSaveBo) {
        return shopTypeService.saveInfo(shopTypeSaveBo);
    }

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

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody ShopTypeUpdateBo shopTypeUpdateBo) {
        return shopTypeService.updateInfo(shopTypeUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ShopTypeInfoBo shopTypeInfoBo) {
        return shopTypeService.delete(shopTypeInfoBo);
    }
}
