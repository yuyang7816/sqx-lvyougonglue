package com.sqx.app.lygl.complaint.complaintInfo.controller;

import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintExamineBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintInfoBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintPageBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintUpdateBo;
import com.sqx.app.lygl.complaint.complaintInfo.service.TravelComplaintService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Tag(name = "管理端-投诉")
@RestController
@RequestMapping("admin/complaint")
public class AdminComplaintController {

    @Resource
    private TravelComplaintService complaintService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody ComplaintPageBo complaintPageBo) {
        return complaintService.pageInfo(complaintPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody ComplaintInfoBo complaintInfoBo) {
        return complaintService.info(complaintInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestAttribute Long userId, @RequestBody ComplaintUpdateBo complaintUpdateBo) {
        complaintUpdateBo.setUserId(userId);
        return complaintService.updateInfo(complaintUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ComplaintInfoBo complaintInfoBo) {
        return complaintService.delete(complaintInfoBo);
    }

    /**
     * 审核
     */
    @Operation(summary = "审核")
    @PostMapping("examine")
    public Result<?> examine(@RequestBody @Validated ComplaintExamineBo complaintExamineBo) {
        return complaintService.examine(complaintExamineBo);
    }
}
