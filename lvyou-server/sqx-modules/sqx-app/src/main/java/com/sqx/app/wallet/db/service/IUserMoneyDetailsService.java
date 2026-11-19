package com.sqx.app.wallet.db.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;

import java.math.BigDecimal;

public interface IUserMoneyDetailsService extends IService<UserMoneyDetails> {
    void saveRefund(Long userId, String orderNo, BigDecimal amount);
}
