package com.sqx.app.wallet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.wallet.domain.bo.FundDetailBo;
import com.sqx.app.wallet.domain.bo.UpdateUserMoneyBo;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import com.sqx.app.wallet.domain.vo.UserMoneyVo;
import com.sqx.app.wallet.service.WalletService;
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
 * @since 2024/8/1
 */
@Tag(name = "管理端-钱包")
@RestController
@RequestMapping("admin/wallet")
public class AdminWalletController {
    @Resource
    private WalletService walletService;

    @Login
    @Operation(summary = "获取钱包详情")
    @GetMapping("info")
    public Result info(Long userId) {
        UserMoneyVo vo = walletService.info(userId);
        return Result.success(vo);
    }

    @Operation(summary = "资金记录")
    @GetMapping("fundDetail")
    public Result<IPage<UserMoneyDetails>> fundDetail(FundDetailBo fundDetailBo) {
        IPage<UserMoneyDetails> voIPage = walletService.fundDetail(fundDetailBo);
        return Result.success(voIPage);
    }

    @Operation(summary = "修改用户余额")
    @GetMapping("updateUserMoney")
    public Result<Void> updateUserMoney(UpdateUserMoneyBo updateUserMoneyBo) {
        walletService.updateUserMoney(updateUserMoneyBo);
        return Result.success();
    }
}
