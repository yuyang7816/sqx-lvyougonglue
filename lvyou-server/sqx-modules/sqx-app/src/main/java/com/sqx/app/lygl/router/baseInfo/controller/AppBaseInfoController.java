package com.sqx.app.lygl.router.baseInfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.router.baseInfo.domain.bo.*;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.service.TravelBaseInfoService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Tag(name = "用户端-路线基本信息")
@RestController
@RequestMapping("app/baseInfo")
public class AppBaseInfoController {

    @Resource
    private TravelBaseInfoService baseInfoService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody BaseInfoSaveBo baseInfoSaveBo) {
        baseInfoSaveBo.setUserId(userId);
        return baseInfoService.saveInfo(baseInfoSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestAttribute Long userId, @RequestBody BaseInfoPageBo baseInfoPageBo) {
        baseInfoPageBo.setCurrUserId(userId);
        return baseInfoService.pageInfo(baseInfoPageBo);
    }

    /**
     * 信息
     */
    @Login
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<BaseInfoVo> info(@RequestAttribute Long userId, @RequestBody @Validated BaseInfoBo baseInfoBo) {
        baseInfoBo.setCurrUserId(userId);
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

    @Operation(summary = "根据城市查询路线")
    @PostMapping("pageByCity")
    public Result<IPage<BaseInfoVo>> pageByCity(@RequestBody BaseInfoPageByCityBo baseInfoPageByCityBo) {
        return baseInfoService.pageByCity(baseInfoPageByCityBo);
    }
}
