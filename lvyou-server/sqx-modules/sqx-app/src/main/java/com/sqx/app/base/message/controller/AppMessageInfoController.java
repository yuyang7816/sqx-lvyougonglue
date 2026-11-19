package com.sqx.app.base.message.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.base.message.domain.bo.MessageInfoInfoBo;
import com.sqx.app.base.message.domain.bo.MessageInfoPageBo;
import com.sqx.app.base.message.domain.bo.MessageInfoSaveBo;
import com.sqx.app.base.message.domain.bo.MessageInfoUpdateBo;
import com.sqx.app.base.message.domain.vo.MessageInfoVo;
import com.sqx.app.base.message.domain.vo.MessageListVo;
import com.sqx.app.base.message.service.MessageInfoService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  前端控制器
 *
 * @author sqx
 * @since 2025-05-19 15:35:07
 */
@Tag(name = "用户端-消息", description = "用户端-消息")
@RestController
@RequestMapping("/app/message")
public class AppMessageInfoController {

    @Resource
    private MessageInfoService messageInfoService;

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @ApiResponse(content = @Content(schema = @Schema(implementation = MessageInfoVo.class)))
    @PostMapping("page")
    public Result<IPage<MessageInfoVo>> page(@RequestAttribute Long userId, @RequestBody @Validated MessageInfoPageBo messageInfoPageBo) {
        messageInfoPageBo.setUserId(userId);
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

    @Login
    @Operation(summary = "消息列表")
    @PostMapping("messageList")
    public Result<MessageListVo> messageList(@RequestAttribute Long userId) {
        return messageInfoService.messageList(userId);
    }
}
