package com.sqx.app.lygl.group.groupUser.controller;

import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserInfoBo;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserPageBo;
import com.sqx.app.lygl.group.groupUser.service.TravelGroupUserService;
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
@Tag(name = "管理端-群聊用户")
@RestController
@RequestMapping("admin/groupUser")
public class AdminGroupUserController {

    @Resource
    private TravelGroupUserService groupUserService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody GroupUserPageBo groupUserPageBo) {
        return groupUserService.pageInfo(groupUserPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody GroupUserInfoBo groupUserInfoBo) {
        return groupUserService.info(groupUserInfoBo);
    }
}
