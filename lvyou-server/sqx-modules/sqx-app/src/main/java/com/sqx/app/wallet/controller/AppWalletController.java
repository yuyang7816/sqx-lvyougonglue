package com.sqx.app.wallet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.wallet.domain.bo.FundDetailBo;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import com.sqx.app.wallet.domain.vo.UserMoneyVo;
import com.sqx.app.wallet.service.WalletService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.shiro.app.annotation.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengming
 * @since 2024/7/23
 */
@Tag(name = "用户端-我的钱包")
@RestController
@RequestMapping("app/wallet")
public class AppWalletController {
    @Autowired
    private WalletService walletService;

    @Login
    @Operation(summary = "获取钱包详情")
    @GetMapping("info")
    public Result info(@RequestAttribute("userId") Long userId) {
        UserMoneyVo vo = walletService.info(userId);
        return Result.success(vo);
    }

    @Login
    @Operation(summary = "资金记录")
    @GetMapping("fundDetail")
    public Result<IPage<UserMoneyDetails>> fundDetail(FundDetailBo fundDetailBo) {
        IPage<UserMoneyDetails> voIPage = walletService.fundDetail(fundDetailBo);
        return Result.success(voIPage);
    }
}
