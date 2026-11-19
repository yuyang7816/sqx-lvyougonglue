package com.sqx.app.lygl.moments.controller;

import com.sqx.app.lygl.moments.domain.bo.MomentsAuditBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsInfoBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsPageBo;
import com.sqx.app.lygl.moments.domain.bo.MomentsUpdateBo;
import com.sqx.app.lygl.moments.service.TravelMomentsService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/10
 *
 * 管理端-动态
 */
@Tag(name = "管理端-动态")
@RestController
@RequestMapping("admin/moments")
public class AdminMomentsController {

    @Resource
    private TravelMomentsService momentsService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody MomentsPageBo momentsPageBo) {
        return momentsService.pageInfo(momentsPageBo.getUserId(), momentsPageBo);
    }

    /**
     * 信息
     */
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

    @Operation(summary = "审核动态")
    @PostMapping("audit")
    public Result<Void> audit(@RequestBody @Validated MomentsAuditBo momentsAuditBo) {
        momentsService.audit(momentsAuditBo);
        return Result.success();
    }
}
