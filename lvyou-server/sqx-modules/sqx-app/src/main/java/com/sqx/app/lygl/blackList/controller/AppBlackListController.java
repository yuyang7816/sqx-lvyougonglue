package com.sqx.app.lygl.blackList.controller;

import com.sqx.app.lygl.blackList.domain.bo.BlackListInfoBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListPageBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListSaveBo;
import com.sqx.app.lygl.blackList.service.TravelBlackListService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/14
 * 用户端-黑名单
 */
@Tag(name = "用户端-黑名单")
@RestController
@RequestMapping("app/blackList")
public class AppBlackListController {

    @Resource
    private TravelBlackListService blackListService;

    /**
     * 保存
     */
    @Schema(description = "保存")
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody BlackListSaveBo blackListSaveBo) {
        blackListSaveBo.setUserId(userId);
        return blackListService.saveInfo(blackListSaveBo);
    }

    /**
     * 列表
     */
    @Schema(description = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody BlackListPageBo blackListPageBo) {
        return blackListService.pageInfo(blackListPageBo);
    }

    /**
     * 信息
     */
    @Schema(description = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody BlackListInfoBo blackListInfoBo) {
        return blackListService.info(blackListInfoBo);
    }

    /**
     * 删除
     */
    @Schema(description = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody BlackListInfoBo blackListInfoBo) {
        return blackListService.delete(blackListInfoBo);
    }
}
