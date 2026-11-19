package com.sqx.app.lygl.index.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.index.domain.bo.LineStatisticsBO;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.bo.StatisticsBo;
import com.sqx.app.lygl.index.domain.vo.*;
import com.sqx.app.lygl.index.service.IndexService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengming
 * @since 2025/9/30
 */
@Tag(name = "管理端-首页统计", description = "管理端-首页统计")
@RestController
@RequestMapping("admin/index")
public class AdminIndexController {

    @Resource
    private IndexService indexService;

    @Operation(summary = "首页统计")
    @GetMapping("indexStatistics")
    public Result<IndexStatisticsVo> indexStatistics() {
        IndexStatisticsVo vo = indexService.indexStatistics();
        return Result.success(vo);
    }

    @Operation(summary = "用户分析")
    @PostMapping("userStatistics")
    public Result<UserStatisticsVo> userStatistics(@RequestBody StatisticsBo statisticsBo) {
        UserStatisticsVo vo = indexService.userStatistics(statisticsBo);
        return Result.success(vo);
    }

    @Operation(summary = "支付统计")
    @PostMapping("payStatistics")
    public Result<PayStatisticsVo> payStatistics(@RequestBody StatisticsBo statisticsBo) {
        PayStatisticsVo vo = indexService.payStatistics(statisticsBo);
        return Result.success(vo);
    }

    @Operation(summary = "支付记录")
    @PostMapping("payDetailPage")
    public Result<IPage<PayDetailsVo>> payDetailPage(@RequestBody PayDetailsPageBo payDetailsPageBo) {
        IPage<PayDetailsVo> voIPage = indexService.payDetailPage(payDetailsPageBo);
        return Result.success(voIPage);
    }

    @Operation(summary = "用户增长折线图")
    @PostMapping("userLine")
    public Result<UserLineVO> userLine(@RequestBody LineStatisticsBO lineStatisticsBO) {
        UserLineVO vo = indexService.userLine(lineStatisticsBO);
        return Result.success(vo);
    }

    @Operation(summary = "演唱会统计")
    @PostMapping("concertStatistics")
    public Result<List<ConcertStatisticsVO>> concertStatistics(@RequestBody StatisticsBo statisticsBo) {
        List<ConcertStatisticsVO> voList = indexService.concertStatistics(statisticsBo);
        return Result.success(voList);
    }

    @Operation(summary = "动态统计")
    @PostMapping("momentStatistics")
    public Result<MomentStatisticsVO> momentStatistics(@RequestBody StatisticsBo statisticsBo) {
        MomentStatisticsVO vo = indexService.momentStatistics(statisticsBo);
        return Result.success(vo);
    }

    @Operation(summary = "待审核统计")
    @PostMapping("examineStatistics")
    public Result<ExamineStatisticsVO> examineStatistics() {
        ExamineStatisticsVO vo = indexService.examineStatistics();
        return Result.success(vo);
    }
}
