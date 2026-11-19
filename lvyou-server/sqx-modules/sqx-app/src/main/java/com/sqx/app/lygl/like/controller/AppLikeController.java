package com.sqx.app.lygl.like.controller;

import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.LikeSaveBo;
import com.sqx.app.lygl.like.service.TravelLikeService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Tag(name = "用户端-用户点赞")
@RestController
@RequestMapping("/app/like")
public class AppLikeController {

    @Resource
    private TravelLikeService likeService;

    /**
     * 喜欢/取消喜欢
     */
    @Operation(summary = "喜欢/取消喜欢")
    @PostMapping("save")
    public Result<?> save(@RequestBody @Validated LikeSaveBo likeSaveBo) {
        return likeService.saveInfo(likeSaveBo);
    }

    /**
     * 列表
     */
    @Login
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestAttribute Long userId, @RequestBody @Validated LikePageBo likePageBo) {
        likePageBo.setUserId(userId);
        return likeService.pageInfo(likePageBo);
    }
}
