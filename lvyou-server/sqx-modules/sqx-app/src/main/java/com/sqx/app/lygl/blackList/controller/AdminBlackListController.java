package com.sqx.app.lygl.blackList.controller;

import com.sqx.app.lygl.blackList.domain.bo.BlackListInfoBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListPageBo;
import com.sqx.app.lygl.blackList.service.TravelBlackListService;
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
 * @since 2024/10/14
 * 管理端-黑名单
 */
@Tag(name = "管理端-黑名单")
@RestController
@RequestMapping("admin/blackList")
public class AdminBlackListController {

    @Resource
    private TravelBlackListService blackListService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody BlackListPageBo blackListPageBo) {
        return blackListService.pageInfo(blackListPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody BlackListInfoBo blackListInfoBo) {
        return blackListService.info(blackListInfoBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody BlackListInfoBo blackListInfoBo) {
        return blackListService.delete(blackListInfoBo);
    }
}
