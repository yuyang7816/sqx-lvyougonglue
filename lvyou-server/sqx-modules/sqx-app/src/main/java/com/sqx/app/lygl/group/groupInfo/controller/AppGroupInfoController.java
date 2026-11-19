package com.sqx.app.lygl.group.groupInfo.controller;

import com.sqx.app.lygl.group.groupInfo.domain.bo.*;
import com.sqx.app.lygl.group.groupInfo.service.TravelGroupInfoService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Tag(name = "用户端-群聊信息")
@RestController
@RequestMapping("app/groupInfo")
public class AppGroupInfoController {

    @Resource
    private TravelGroupInfoService groupInfoService;

    /**
     * 申请创建群聊
     */
    @Operation(summary = "申请创建群聊")
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody @Validated GroupInfoSaveBo groupInfoSaveBo) {
        groupInfoSaveBo.setUserId(userId);
        return groupInfoService.saveInfo(groupInfoSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestAttribute Long userId, @RequestBody GroupInfoPageBo groupInfoPageBo) {
        groupInfoPageBo.setCurrUserId(userId);
        return groupInfoService.pageInfo(groupInfoPageBo);
    }

    /**
     * 信息
     */
    @Login
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestAttribute Long userId, @RequestBody @Validated GroupInfoBo groupInfoBo) {
        groupInfoBo.setCurrUserId(userId);
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
     * 加入群聊
     */
    @Operation(summary = "加入群聊")
    @Login
    @PostMapping("join")
    public Result<?> join(@RequestAttribute Long userId, @RequestBody GroupInfoBo groupInfoBo) {
        return groupInfoService.join(userId, groupInfoBo);
    }

    @Operation(summary = "我创建的群聊")
    @Login
    @PostMapping("owner")
    public Result<?> owner(@RequestAttribute Long userId, @RequestBody GroupOwnerBo groupOwnerBo) {
        groupOwnerBo.setUserId(userId);
        return groupInfoService.owner(groupOwnerBo);
    }

    @Operation(summary = "我加入的群聊")
    @Login
    @PostMapping("joinList")
    public Result<?> joinList(@RequestAttribute Long userId, @RequestBody GroupOwnerBo groupOwnerBo) {
        groupOwnerBo.setUserId(userId);
        return groupInfoService.joinList(groupOwnerBo);
    }

    @Operation(summary = "启用/禁用")
    @Login
    @PostMapping("enable")
    public Result<?> enable(@RequestAttribute Long userId, @RequestBody GroupEnableBo groupEnableBo) {
        groupEnableBo.setUserId(userId);
        return groupInfoService.enable(groupEnableBo);
    }
}
