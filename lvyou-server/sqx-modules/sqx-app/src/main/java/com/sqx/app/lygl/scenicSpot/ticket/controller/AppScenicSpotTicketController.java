package com.sqx.app.lygl.scenicSpot.ticket.controller;

import com.sqx.app.lygl.scenicSpot.ticket.domain.bo.ScenicSpotTicketPageBo;
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
@Tag(name = "用户端-景点门票")
@RestController
@RequestMapping("app/scenicSpotTicket")
public class AppScenicSpotTicketController {

    @Resource
    private TravelScenicSpotTicketService scenicSpotTicketService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody ScenicSpotTicketPageBo scenicSpotTicketPageBo) {
        return scenicSpotTicketService.pageInfo(scenicSpotTicketPageBo);
    }
}
