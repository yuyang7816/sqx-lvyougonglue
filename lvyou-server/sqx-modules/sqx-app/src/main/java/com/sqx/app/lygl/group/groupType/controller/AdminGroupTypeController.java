package com.sqx.app.lygl.group.groupType.controller;

import com.sqx.app.lygl.group.groupType.domain.bo.*;
import com.sqx.app.lygl.group.groupType.service.TravelGroupTypeService;
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
 */
@Tag(name = "管理端-群聊类型")
@RestController
@RequestMapping("admin/groupType")
public class AdminGroupTypeController {

    @Resource
    private TravelGroupTypeService groupTypeService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody @Validated GroupTypeSaveBo groupTypeSaveBo) {
        return groupTypeService.saveInfo(groupTypeSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody GroupTypePageBo groupTypePageBo) {
        return groupTypeService.pageInfo(groupTypePageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody GroupTypeInfoBo groupTypeInfoBo) {
        return groupTypeService.info(groupTypeInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody GroupTypeUpdateBo groupTypeUpdateBo) {
        return groupTypeService.updateInfo(groupTypeUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody GroupTypeInfoBo groupTypeInfoBo) {
        return groupTypeService.delete(groupTypeInfoBo);
    }

    /**
     * 更新状态
     */
    @Operation(summary = "更新状态")
    @PostMapping("updateStatus")
    public Result<?> updateStatus(@RequestBody GroupTypeUpdateStatusBo groupTypeUpdateStatusBo) {
        return groupTypeService.updateStatus(groupTypeUpdateStatusBo);
    }

}
