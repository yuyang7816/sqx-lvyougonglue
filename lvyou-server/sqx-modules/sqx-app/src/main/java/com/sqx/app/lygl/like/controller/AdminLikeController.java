package com.sqx.app.lygl.like.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.app.lygl.like.service.TravelLikeService;
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
 * @since 2024/10/11
 */
@Tag(name = "管理端-用户点赞")
@RestController
@RequestMapping("/admin/like")
public class AdminLikeController {

    @Resource
    private TravelLikeService likeService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody @Validated LikePageBo likePageBo) {
        return likeService.pageInfo(likePageBo);
    }

    @Operation(summary = "动态点赞列表")
    @PostMapping("momentLikePage")
    public Result<IPage<MomentLikePageVo>> momentLikePage(@RequestBody @Validated MomentLikePageBo momentLikePageBo) {
        IPage<MomentLikePageVo> voIPage = likeService.momentLikePage(momentLikePageBo);
        return Result.success(voIPage);
    }
}
