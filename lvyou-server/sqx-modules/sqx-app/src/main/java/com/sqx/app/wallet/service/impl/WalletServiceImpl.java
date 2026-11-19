package com.sqx.app.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.wallet.db.mapper.UserMoneyDetailsDao;
import com.sqx.app.wallet.db.service.IUserMoneyService;
import com.sqx.app.wallet.domain.bo.FundDetailBo;
import com.sqx.app.wallet.domain.bo.UpdateUserMoneyBo;
import com.sqx.app.wallet.domain.entity.UserMoney;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import com.sqx.app.wallet.domain.vo.UserMoneyVo;
import com.sqx.app.wallet.service.WalletService;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/23
 */
@Slf4j
@Service
public class WalletServiceImpl implements WalletService {
    @Resource
    private IUserMoneyService userMoneyService;
    @Resource
    private UserMoneyDetailsDao userMoneyDetailsDao;

    private UserMoney getUserMoney(Long userId) {
        QueryWrapper<UserMoney> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserMoney::getUserId, userId);
        UserMoney userMoney = userMoneyService.getOne(queryWrapper);
        if (Objects.isNull(userMoney)) {
            throw new SqxException("用户钱包不存在");
        }

        return userMoney;
    }

    @Override
    public UserMoneyVo info(Long userId) {
        UserMoney userMoney = getUserMoney(userId);

        UserMoneyVo vo = new UserMoneyVo();
        BeanUtils.copyProperties(userMoney, vo);

        // 累计充值金额
        BigDecimal totalRecharge = userMoneyDetailsDao.sumTotalRecharge(userId);

        vo.setTotalRecharge(totalRecharge);
        return vo;
    }

    @Override
    public IPage<UserMoneyDetails> fundDetail(FundDetailBo fundDetailBo) {
        Page<UserMoneyDetails> initPage = PageUtil.initPage(fundDetailBo.getPage(), fundDetailBo.getLimit());

        QueryWrapper<UserMoneyDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(fundDetailBo.getUserId()), UserMoneyDetails::getUserId, fundDetailBo.getUserId())
                .eq(Objects.nonNull(fundDetailBo.getClassify()), UserMoneyDetails::getClassify, fundDetailBo.getClassify())
                .eq(Objects.nonNull(fundDetailBo.getType()), UserMoneyDetails::getType, fundDetailBo.getType())
                .eq(Objects.nonNull(fundDetailBo.getState()), UserMoneyDetails::getState, fundDetailBo.getState())
                .between(Objects.nonNull(fundDetailBo.getStartTime()) && Objects.nonNull(fundDetailBo.getEndTime()), UserMoneyDetails::getCreateTime, fundDetailBo.getStartTime(), fundDetailBo.getEndTime())
                .orderByDesc(UserMoneyDetails::getId);

        Page<UserMoneyDetails> userMoneyDetailsPage = userMoneyDetailsDao.selectPage(initPage, queryWrapper);
        return userMoneyDetailsPage;
    }

    @Override
    public void updateUserMoney(UpdateUserMoneyBo updateUserMoneyBo) {
        if ("incr".equals(updateUserMoneyBo.getOpt())) {
            this.incrBalance(updateUserMoneyBo.getUserId(), updateUserMoneyBo.getAmount());
        }

        if ("decr".equals(updateUserMoneyBo.getOpt())) {
            this.decrBalance(updateUserMoneyBo.getUserId(), updateUserMoneyBo.getAmount());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void incrBalance(Long userId, BigDecimal amount) {
        UpdateWrapper<UserMoney> moneyUpdateWrapper = new UpdateWrapper<>();
        moneyUpdateWrapper.lambda()
                .eq(UserMoney::getUserId, userId)
                .setSql("money = money + {0}", amount);

        userMoneyService.update(new UserMoney(), moneyUpdateWrapper);
    }

    /**
     * 扣除用户钱包余额
     * @param userId 用户ID
     * @param amount 金额
     */
    @Transactional(rollbackFor = Exception.class)
    public void decrBalance(Long userId, BigDecimal amount) {
        UpdateWrapper<UserMoney> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(UserMoney::getUserId, userId)
                .setSql("money = money - {0}", amount);

        userMoneyService.update(new UserMoney(), updateWrapper);
    }
}
