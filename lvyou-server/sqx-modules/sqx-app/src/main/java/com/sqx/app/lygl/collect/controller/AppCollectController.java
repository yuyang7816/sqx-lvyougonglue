package com.sqx.app.lygl.collect.controller;

import com.sqx.app.lygl.collect.domain.bo.CollectPageBo;
import com.sqx.app.lygl.collect.domain.bo.CollectSaveBo;
import com.sqx.app.lygl.collect.service.TravelCollectService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Tag(name = "用户端-用户收藏")
@RestController
@RequestMapping("app/collect")
public class AppCollectController {

    @Resource
    private TravelCollectService collectService;

    /**
     * 收藏/取消收藏
     */
    @Operation(summary = "收藏/取消收藏")
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody @Validated CollectSaveBo collectSaveBo) {
        collectSaveBo.setUserId(userId);
        return collectService.saveInfo(collectSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestAttribute Long userId, @RequestBody @Validated CollectPageBo collectPageBo) {
        collectPageBo.setUserId(userId);
        return collectService.pageInfo(collectPageBo);
    }
}
