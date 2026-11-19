package com.sqx.app.lygl.collect.controller;

import com.sqx.app.lygl.collect.domain.bo.CollectPageBo;
import com.sqx.app.lygl.collect.service.TravelCollectService;
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
 * @since 2024/10/11
 */
@Tag(name = "管理端-用户收藏")
@RestController
@RequestMapping("admin/collect")
public class AdminCollectController {

    @Resource
    private TravelCollectService collectService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody @Validated CollectPageBo collectPageBo) {
        return collectService.pageInfo(collectPageBo);
    }
}
