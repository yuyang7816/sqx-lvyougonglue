package com.sqx.app.lygl.background.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.background.domain.bo.UserBackgroundInfoBo;
import com.sqx.app.lygl.background.domain.bo.UserBackgroundPageBo;
import com.sqx.app.lygl.background.domain.bo.UserBackgroundSaveBo;
import com.sqx.app.lygl.background.domain.bo.UserBackgroundUpdateBo;
import com.sqx.app.lygl.background.domain.vo.UserBackgroundVo;
import com.sqx.app.lygl.background.service.UserBackgroundService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户背景图 前端控制器
 *
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
@Tag(name = "管理端-用户背景图", description = "管理端-用户背景图")
@RestController
@RequestMapping("/admin/userBackground")
public class AdminUserBackgroundController {

    @Resource
    private UserBackgroundService userBackgroundService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated UserBackgroundSaveBo userBackgroundSaveBo) {
        return userBackgroundService.saveInfo(userBackgroundSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserBackgroundVo.class)))
    @PostMapping("page")
    public Result<IPage<UserBackgroundVo>> page(@RequestBody @Validated UserBackgroundPageBo userBackgroundPageBo) {
        return userBackgroundService.pageInfo(userBackgroundPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserBackgroundVo.class)))
    @PostMapping("info")
    public Result<UserBackgroundVo> info(@RequestBody @Validated UserBackgroundInfoBo userBackgroundInfoBo) {
        return userBackgroundService.info(userBackgroundInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated UserBackgroundUpdateBo userBackgroundUpdateBo) {
        return userBackgroundService.updateInfo(userBackgroundUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated UserBackgroundInfoBo userBackgroundInfoBo) {
        return userBackgroundService.delete(userBackgroundInfoBo);
    }
}
