package com.sqx.app.lygl.background.controller;


import com.sqx.app.lygl.background.domain.bo.UserBackgroundBindBo;
import com.sqx.app.lygl.background.domain.vo.UserBackgroundVo;
import com.sqx.app.lygl.background.service.UserBackgroundService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengming
 * @since 2025/6/9
 */
@Tag(name = "用户端-用户背景图", description = "用户端-用户背景图")
@RestController
@RequestMapping("/app/userBackground")
public class AppUserBackgroundController {

    @Resource
    private UserBackgroundService userBackgroundService;

    /**
     * 列表
     */
    @Operation(summary = "列表(不分页)")
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserBackgroundVo.class)))
    @PostMapping("list")
    public Result<List<UserBackgroundVo>> list() {
        return userBackgroundService.list();
    }

    @Login
    @Operation(summary = "绑定/修改")
    @PostMapping("bind")
    public Result<Void> bind(@RequestAttribute Long userId, @RequestBody UserBackgroundBindBo userBackgroundBindBo) {
        userBackgroundBindBo.setUerId(userId);
        return userBackgroundService.bind(userBackgroundBindBo);
    }
}
