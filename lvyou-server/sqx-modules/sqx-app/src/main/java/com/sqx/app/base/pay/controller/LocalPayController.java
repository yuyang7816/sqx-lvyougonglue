package com.sqx.app.base.pay.controller;

import com.sqx.app.base.constants.BaseConstant;
import com.sqx.app.base.pay.domain.BusinessParam;
import com.sqx.app.base.pay.service.LocalPayService;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengming
 * @since 2025/7/9
 */
@Tag(name = "余额支付接口", description = "余额支付接口")
@RestController
@RequestMapping(BaseConstant.BASE_APP_PREFIX + "/local")
public class LocalPayController {

    @Autowired
    private LocalPayService localPayService;

    @Operation(summary = "余额支付")
    @PostMapping("pay")
    public Result<String> pay(@RequestBody BusinessParam param) {
        return localPayService.pay(param);
    }
}
