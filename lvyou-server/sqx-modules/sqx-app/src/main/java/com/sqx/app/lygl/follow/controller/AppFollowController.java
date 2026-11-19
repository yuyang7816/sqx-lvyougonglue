package com.sqx.app.lygl.follow.controller;

import com.sqx.app.lygl.follow.domain.bo.FollowPageBo;
import com.sqx.app.lygl.follow.domain.bo.FollowSaveBo;
import com.sqx.app.lygl.follow.service.TravelFollowService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Tag(name = "用户端-用户关注")
@RestController
@RequestMapping("app/follow")
public class AppFollowController {

    @Resource
    private TravelFollowService followService;

    /**
     * 关注/取消关注
     */
    @Login
    @PostMapping("save")
    public Result<?> save(@RequestAttribute Long userId, @RequestBody FollowSaveBo followSaveBo) {
        followSaveBo.setUserId(userId);
        return followService.saveInfo(followSaveBo);
    }

    /**
     * 列表
     */
    @PostMapping("page")
    public Result<?> page(@RequestBody FollowPageBo followPageBo) {
        return followService.pageInfo(followPageBo);
    }
}
