package com.sqx.app.lygl.moments.controller;

import com.sqx.app.lygl.moments.domain.bo.MomentsInfoBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsPageBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsSaveBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsUpdateBo;
import com.sqx.app.lygl.moments.service.TravelMomentsService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 *
 * 用户端-动态
 */
@Tag(name = "用户端-动态")
@RestController
@RequestMapping("app/moments")
public class AppMomentsController {

    @Resource
    private TravelMomentsService momentsService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody MomentsSaveBo momentsSaveBo) {
        momentsSaveBo.setUserId(userId);
        return momentsService.saveInfo(momentsSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestAttribute Long userId, @RequestBody MomentsPageBo momentsPageBo) {
        return momentsService.pageInfo(userId, momentsPageBo);
    }

    /**
     * 信息
     */
    @Login
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody MomentsInfoBo momentsInfoBo) {
        return momentsService.info(momentsInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody MomentsUpdateBo momentsUpdateBo) {
        return momentsService.updateInfo(momentsUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody MomentsInfoBo momentsInfoBo) {
        return momentsService.delete(momentsInfoBo);
    }
}
