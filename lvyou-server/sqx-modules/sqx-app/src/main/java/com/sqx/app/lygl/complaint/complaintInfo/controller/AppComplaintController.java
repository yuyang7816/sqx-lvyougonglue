package com.sqx.app.lygl.complaint.complaintInfo.controller;

import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintInfoBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintPageBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintSaveBo;
import com.sqx.app.lygl.complaint.complaintInfo.domain.bo.ComplaintUpdateBo;
import com.sqx.app.lygl.complaint.complaintInfo.service.TravelComplaintService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/14
 */
@Tag(name = "用户端-投诉")
@RestController
@RequestMapping("app/complaint")
public class AppComplaintController {

    @Resource
    private TravelComplaintService complaintService;

    /**
     * 保存
     */
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody ComplaintSaveBo complaintSaveBo) {
        complaintSaveBo.setUserId(userId);
        return complaintService.saveInfo(complaintSaveBo);
    }

    /**
     * 列表
     */
    @PostMapping("page")
    public Result<?> page(@RequestBody ComplaintPageBo complaintPageBo) {
        return complaintService.pageInfo(complaintPageBo);
    }

    /**
     * 信息
     */
    @PostMapping("info")
    public Result<?> info(@RequestBody ComplaintInfoBo complaintInfoBo) {
        return complaintService.info(complaintInfoBo);
    }

    /**
     * 更新
     */
    @Login
    @PostMapping("update")
    public Result<?> update(@RequestAttribute Long userId, @RequestBody ComplaintUpdateBo complaintUpdateBo) {
        complaintUpdateBo.setUserId(userId);
        return complaintService.updateInfo(complaintUpdateBo);
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ComplaintInfoBo complaintInfoBo) {
        return complaintService.delete(complaintInfoBo);
    }
}
