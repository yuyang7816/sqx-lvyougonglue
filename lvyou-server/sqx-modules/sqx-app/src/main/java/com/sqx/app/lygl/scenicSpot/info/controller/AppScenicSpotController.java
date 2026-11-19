package com.sqx.app.lygl.scenicSpot.info.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotInfoBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotPageBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotSaveBo;
import com.sqx.app.lygl.scenicSpot.info.domain.bo.ScenicSpotUpdateBo;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.service.TravelScenicSpotService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Tag(name = "用户端-景点信息")
@RestController
@RequestMapping("app/scenicSpot")
public class AppScenicSpotController {

    @Resource
    private TravelScenicSpotService scenicSpotService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody ScenicSpotSaveBo scenicSpotSaveBo){
        return scenicSpotService.saveInfo(scenicSpotSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<IPage<ScenicSpotVo>> page(@RequestAttribute Long userId, @RequestBody ScenicSpotPageBo scenicSpotPageBo) {
        return scenicSpotService.pageInfo(userId, scenicSpotPageBo);
    }

    /**
     * 信息
     */
    @Login
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestAttribute Long userId, @RequestBody ScenicSpotInfoBo scenicSpotInfoBo) {
        return scenicSpotService.info(userId, scenicSpotInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody ScenicSpotUpdateBo scenicSpotUpdateBo) {
        return scenicSpotService.updateInfo(scenicSpotUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ScenicSpotInfoBo scenicSpotInfoBo) {
        return scenicSpotService.delete(scenicSpotInfoBo);
    }
}
