package com.sqx.app.wallet.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.wallet.db.mapper.UserMoneyDao;
import com.sqx.app.wallet.db.service.IUserMoneyService;
import com.sqx.app.wallet.domain.entity.UserMoney;
import com.sqx.domain.db.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class IUserMoneyServiceImpl extends ServiceImpl<UserMoneyDao, UserMoney> implements IUserMoneyService {

    @Resource
    private UserMoneyDao userMoneyDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void incr(Long userId, BigDecimal amount) {
        userMoneyDao.incr(userId, amount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void decr(Long userId, BigDecimal amount) {
        userMoneyDao.decr(userId, amount);
    }

    @Override
    public UserMoney getByUserId(Long userId) {
        LambdaQueryWrapper<UserMoney> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserMoney::getUserId, userId);

        return this.getOne(queryWrapper);
    }
}
