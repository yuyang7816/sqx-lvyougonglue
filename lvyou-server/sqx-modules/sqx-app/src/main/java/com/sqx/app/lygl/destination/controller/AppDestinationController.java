package com.sqx.app.lygl.destination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.destination.domain.bo.DestinationPageBo;
import com.sqx.app.lygl.destination.domain.vo.DestinationVo;
import com.sqx.app.lygl.destination.service.DestinationService;
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
 * 目的地配置 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
@Tag(name = "用户端-目的地配置", description = "用户端-目的地配置")
@RestController
@RequestMapping("/app/destination")
public class AppDestinationController {

    @Resource
    private DestinationService destinationService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DestinationVo.class)))
    @PostMapping("page")
    public Result<IPage<DestinationVo>> page(@RequestBody @Validated DestinationPageBo destinationPageBo) {
        IPage<DestinationVo> voIPage = destinationService.pageInfo(destinationPageBo);
        return Result.success(voIPage);
    }
}
