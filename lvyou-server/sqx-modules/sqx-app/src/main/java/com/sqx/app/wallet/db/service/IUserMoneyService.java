package com.sqx.app.wallet.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.wallet.domain.entity.UserMoney;

import java.math.BigDecimal;

public interface IUserMoneyService extends IService<UserMoney> {

    void incr(Long userId, BigDecimal amount);

    void decr(Long userId, BigDecimal amount);

    UserMoney getByUserId(Long userId);
}
