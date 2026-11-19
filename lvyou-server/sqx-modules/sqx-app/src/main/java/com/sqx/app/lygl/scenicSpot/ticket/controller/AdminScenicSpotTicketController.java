package com.sqx.app.lygl.scenicSpot.ticket.controller;

import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketInfoBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketPageBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketSaveBo;
import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketUpdateBo;
import com.sqx.app.lygl.scenicSpot.ticket.service.TravelScenicSpotTicketService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "管理端-景点门票")
@RestController
@RequestMapping("admin/scenicSpotTicket")
public class AdminScenicSpotTicketController {

    @Resource
    private TravelScenicSpotTicketService scenicSpotTicketService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody ScenicSpotTicketSaveBo scenicSpotTicketSaveBo) {
        return scenicSpotTicketService.saveInfo(scenicSpotTicketSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody ScenicSpotTicketPageBo scenicSpotTicketPageBo) {
        return scenicSpotTicketService.pageInfo(scenicSpotTicketPageBo);
    }

    /**
     * 信息
     */

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody ScenicSpotTicketUpdateBo scenicSpotTicketUpdateBo) {
        return scenicSpotTicketService.updateInfo(scenicSpotTicketUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ScenicSpotTicketInfoBo scenicSpotTicketInfoBo) {
        return scenicSpotTicketService.delete(scenicSpotTicketInfoBo);
    }
}
