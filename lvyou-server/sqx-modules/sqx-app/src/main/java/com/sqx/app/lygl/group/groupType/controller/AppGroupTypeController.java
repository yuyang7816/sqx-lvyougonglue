package com.sqx.app.lygl.group.groupType.controller;

import com.sqx.app.lygl.group.groupType.domain.bo.GroupTypeInfoBo;
import com.sqx.app.lygl.group.groupType.domain.bo.GroupTypePageBo;
import com.sqx.app.lygl.group.groupType.service.TravelGroupTypeService;
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
 */
@Tag(name = "用户端-群聊类型")
@RestController
@RequestMapping("app/groupType")
public class AppGroupTypeController {

    @Resource
    private TravelGroupTypeService groupTypeService;

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
}
