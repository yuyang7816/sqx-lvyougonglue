package com.sqx.app.lygl.follow.controller;

import com.sqx.app.lygl.follow.domain.bo.FollowPageBo;
import com.sqx.app.lygl.follow.service.TravelFollowService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Tag(name = "管理端-用户关注")
@RestController
@RequestMapping("admin/follow")
public class AdminFollowController {

    @Resource
    private TravelFollowService followService;

    /**
     * 列表
     */
    @PostMapping("page")
    public Result<?> page(@RequestBody FollowPageBo followPageBo) {
        return followService.pageInfo(followPageBo);
    }
}
