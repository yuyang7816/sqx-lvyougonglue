package com.sqx.app.lygl.concert.controller;

import com.sqx.app.lygl.concert.domain.bo.ConcertInfoBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertPageBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertSaveBo;
import com.sqx.app.lygl.concert.domain.bo.ConcertUpdateBo;
import com.sqx.app.lygl.concert.service.TravelConcertService;
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
 * @since 2024/10/10
 *
 * 用户端-演唱会
 */
@Tag(name = "用户端-演唱会")
@RestController
@RequestMapping("app/concert")
public class AppConcertController {

    @Resource
    private TravelConcertService concertService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody ConcertSaveBo concertSaveBo) {
        return concertService.saveInfo(concertSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody ConcertPageBo concertPageBo) {
        return concertService.pageInfo(concertPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody ConcertInfoBo concertInfoBo) {
        return concertService.info(concertInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> info(@RequestBody ConcertUpdateBo concertUpdateBo) {
        return concertService.updateInfo(concertUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody ConcertInfoBo concertInfoBo) {
        return concertService.delete(concertInfoBo);
    }

}
