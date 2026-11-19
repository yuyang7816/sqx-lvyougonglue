package com.sqx.app.lygl.router.baseInfo.controller;

import com.sqx.app.lygl.router.baseInfo.domain.bo.*;
import com.sqx.app.lygl.router.baseInfo.service.TravelBaseInfoService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Tag(name = "管理端-路线基本信息")
@RestController
@RequestMapping("admin/baseInfo")
public class AdminBaseInfoController {

    @Resource
    private TravelBaseInfoService baseInfoService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody BaseInfoSaveBo baseInfoSaveBo) {
        return baseInfoService.saveInfo(baseInfoSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody BaseInfoPageBo baseInfoPageBo) {
        return baseInfoService.pageInfo(baseInfoPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody @Validated BaseInfoBo baseInfoBo) {
        return baseInfoService.info(baseInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody @Validated BaseUpdateBo baseUpdateBo) {
        return baseInfoService.updateInfo(baseUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody @Validated BaseInfoBo baseInfoBo) {
        return baseInfoService.delete(baseInfoBo);
    }

    /**
     * 更新状态
     */
    @Operation(summary = "更新状态")
    @PostMapping("updateStatus")
    public Result<?> updateStatus(@RequestBody @Validated BaseInfoUpdateBo baseInfoUpdateBo) {
        return baseInfoService.updateStatus(baseInfoUpdateBo);
    }
}
