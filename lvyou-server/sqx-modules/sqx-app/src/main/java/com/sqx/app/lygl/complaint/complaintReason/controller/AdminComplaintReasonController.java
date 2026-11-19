package com.sqx.app.lygl.complaint.complaintReason.controller;

import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonInfoBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonPageBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonSaveBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonUpdateBo;
import com.sqx.app.lygl.complaint.complaintReason.service.TravelComplaintReasonService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Tag(name = "管理端-投诉原因")
@RestController
@RequestMapping("admin/complaintReason")
public class AdminComplaintReasonController {

    @Resource
    private TravelComplaintReasonService complaintReasonService;

    /**
     * 保存
     */
    @PostMapping("save")
    public Result<?> save(@RequestBody ComplaintReasonSaveBo complaintReasonSaveBo) {
        return complaintReasonService.saveInfo(complaintReasonSaveBo);
    }

    /**
     * 列表
     */
    @PostMapping("page")
    public Result<?> page(@RequestBody ComplaintReasonPageBo complaintReasonPageBo) {
        return complaintReasonService.pageInfo(complaintReasonPageBo);
    }

    /**
     * 信息
     */
    @PostMapping("info")
    public Result<?> info(@RequestBody ComplaintReasonInfoBo complaintReasonInfoBo) {
        return complaintReasonService.info(complaintReasonInfoBo);
    }

    /**
     * 更新
     */
    @PostMapping("update")
    public Result<?> update(@RequestBody ComplaintReasonUpdateBo complaintReasonUpdateBo) {
        return complaintReasonService.updateInfo(complaintReasonUpdateBo);
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ComplaintReasonInfoBo complaintReasonInfoBo) {
        return complaintReasonService.delete(complaintReasonInfoBo);
    }
}
