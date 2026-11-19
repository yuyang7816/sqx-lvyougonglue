package com.sqx.app.lygl.group.groupInfo.controller;

import com.sqx.app.lygl.group.groupInfo.domain.bo.*;
import com.sqx.app.lygl.group.groupInfo.service.TravelGroupInfoService;
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
 * @since 2024/10/12
 *
 * 管理端-群聊信息
 */
@Tag(name = "管理端-群聊信息")
@RestController
@RequestMapping("admin/groupInfo")
public class AdminGroupInfoController {

    @Resource
    private TravelGroupInfoService groupInfoService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody GroupInfoPageBo groupInfoPageBo) {
        return groupInfoService.pageInfo(groupInfoPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody @Validated GroupInfoBo groupInfoBo) {
        return groupInfoService.info(groupInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody @Validated GroupInfoUpdateBo groupInfoUpdateBo) {
        return groupInfoService.updateInfo(groupInfoUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody @Validated GroupInfoBo groupInfoBo) {
        return groupInfoService.delete(groupInfoBo);
    }

    /**
     * 更新状态
     */
    @Operation(summary = "更新状态")
    @PostMapping("updateStatus")
    public Result<?> updateStatus(@RequestBody @Validated GroupInfoUpdateStatusBo groupInfoUpdateStatusBo) {
        return groupInfoService.updateStatus(groupInfoUpdateStatusBo);
    }

    /**
     * 审核群聊创建
     */
    @Operation(summary = "审核群聊创建")
    @PostMapping("examine")
    public Result<?> examine(@RequestBody @Validated GroupInfoExamineBo groupInfoExamineBo) {
        return groupInfoService.examine(groupInfoExamineBo);
    }
}
