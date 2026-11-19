package com.sqx.app.wallet.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.wallet.db.mapper.UserMoneyDetailsDao;
import com.sqx.app.wallet.db.service.IUserMoneyDetailsService;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class IUserMoneyDetailsServiceImpl extends ServiceImpl<UserMoneyDetailsDao, UserMoneyDetails> implements IUserMoneyDetailsService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRefund(Long userId, String orderNo, BigDecimal amount) {
        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(userId);
        userMoneyDetails.setMoney(amount);
        userMoneyDetails.setTitle("退款");
        userMoneyDetails.setContent(String.format("订单【%s】退款【%s】", orderNo, amount));
        userMoneyDetails.setClassify(6);
        userMoneyDetails.setType(1);
        userMoneyDetails.setState(2);

        this.save(userMoneyDetails);
    }
}
