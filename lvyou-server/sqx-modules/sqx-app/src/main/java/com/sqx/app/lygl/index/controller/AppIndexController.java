package com.sqx.app.lygl.index.controller;

import com.sqx.app.lygl.index.domain.vo.MyDataVo;
import com.sqx.app.lygl.index.service.IndexService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2025/10/10
 */
@Tag(name = "用户端-首页", description = "用户端-首页")
@RestController
@RequestMapping("app/index")
public class AppIndexController {

    @Resource
    private IndexService indexService;

    @Login
    @Operation(summary = "首页统计")
    @GetMapping("myData")
    public Result<MyDataVo> myData(@RequestAttribute Long userId) {
        MyDataVo vo = indexService.myData(userId);
        return Result.success(vo);
    }
}
