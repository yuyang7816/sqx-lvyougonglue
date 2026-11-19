package com.sqx.app.lygl.complaint.complaintReason.controller;

import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonInfoBo;
import com.sqx.app.lygl.complaint.complaintReason.domain.bo.ComplaintReasonPageBo;
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
 *
 * 用户端-投诉原因
 */
@Tag(name = "用户端-投诉原因")
@RestController
@RequestMapping("app/complaintReason")
public class AppComplaintReasonController {

    @Resource
    private TravelComplaintReasonService complaintReasonService;

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
}
