package com.sqx.app.base.message.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.base.message.domain.bo.*;
import com.sqx.app.base.message.domain.vo.MessageInfoVo;
import com.sqx.app.base.message.service.MessageInfoService;
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
 *  前端控制器
 *
 * @author sqx
 * @since 2025-05-19 15:35:07
 */
@Tag(name = "管理端-消息", description = "管理端-消息")
@RestController
@RequestMapping("/admin/message")
public class AdminMessageInfoController {

    @Resource
    private MessageInfoService messageInfoService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated MessageInfoSaveBo messageInfoSaveBo) {
        return messageInfoService.saveInfo(messageInfoSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @ApiResponse(content = @Content(schema = @Schema(implementation = MessageInfoVo.class)))
    @PostMapping("page")
    public Result<IPage<MessageInfoVo>> page(@RequestBody @Validated MessageInfoPageBo messageInfoPageBo) {
        return messageInfoService.pageInfo(messageInfoPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = MessageInfoVo.class)))
    @PostMapping("info")
    public Result<MessageInfoVo> info(@RequestBody @Validated MessageInfoInfoBo messageInfoInfoBo) {
        return messageInfoService.info(messageInfoInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated MessageInfoUpdateBo messageInfoUpdateBo) {
        return messageInfoService.updateInfo(messageInfoUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated MessageInfoInfoBo messageInfoInfoBo) {
        return messageInfoService.delete(messageInfoInfoBo);
    }

    @Operation(summary = "推送消息")
    @PostMapping("push")
    public Result<Void> push(@RequestBody @Validated MessagePushBo messagePushBo) {
        messageInfoService.push(messagePushBo);
        return Result.success();
    }
}
