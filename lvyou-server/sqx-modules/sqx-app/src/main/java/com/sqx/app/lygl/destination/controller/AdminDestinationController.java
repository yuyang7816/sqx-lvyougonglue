package com.sqx.app.lygl.destination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.destination.domain.bo.*;
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
@Tag(name = "管理端-目的地配置", description = "管理端-目的地配置")
@RestController
@RequestMapping("/admin/destination")
public class AdminDestinationController {

    @Resource
    private DestinationService destinationService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated DestinationSaveBo destinationSaveBo) {
        destinationService.saveInfo(destinationSaveBo);
        return Result.success();
    }

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

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DestinationVo.class)))
    @PostMapping("info")
    public Result<DestinationVo> info(@RequestBody @Validated DestinationInfoBo destinationInfoBo) {
        DestinationVo vo = destinationService.info(destinationInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated DestinationUpdateBo destinationUpdateBo) {
        destinationService.updateInfo(destinationUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated DestinationInfoBo destinationInfoBo) {
        destinationService.delete(destinationInfoBo);
        return Result.success();
    }

    @Operation(summary = "上传相册图")
    @PostMapping("addAlbum")
    public Result<Void> addAlbum(AddAlbumBo addAlbumBo) {
        destinationService.addAlbum(addAlbumBo);
        return Result.success();
    }
}
