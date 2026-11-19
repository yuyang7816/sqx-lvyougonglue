package com.sqx.app.base.pay.service.impl;

import com.sqx.app.base.pay.business.service.BusinessService;
import com.sqx.app.base.pay.business.service.CallbackService;
import com.sqx.app.base.pay.domain.BusinessParam;
import com.sqx.app.base.pay.enums.PlatformEnum;
import com.sqx.app.base.pay.service.LocalPayService;
import com.sqx.app.wallet.db.mapper.UserMoneyDao;
import com.sqx.app.wallet.db.service.IUserMoneyDetailsService;
import com.sqx.app.wallet.domain.entity.UserMoney;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.utils.GenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author chengming
 * @since 2025/7/9
 */
@Service
public class LocalPayServiceImpl implements LocalPayService {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private CallbackService callbackService;
    @Autowired
    private UserMoneyDao userMoneyDao;
    @Autowired
    private IUserMoneyDetailsService iUserMoneyDetailsService;

    @Override
    public Result<String> pay(BusinessParam param) {
        String orderNo = GenUtil.genOrderNo();
        param.setOrderNo(orderNo);

        UserMoney userMoney = userMoneyDao.selectByUserId(param.getUserId());
        if (Objects.isNull(userMoney)) {
            throw new BizException("用户钱包不存在");
        }

        if (param.getAmount().compareTo(userMoney.getMoney()) > 0) {
            throw new BizException("余额不足");
        }

        businessService.process(param.getOrderNo(), "5", param);
        callbackService.successProcess(param.getOrderNo(), param.getOrderNo());

        // 扣除余额
        userMoneyDao.decr(param.getUserId(), param.getAmount());

        // 记录余额信息
        saveUserMoneyDetails(param);

        return Result.success("");
    }

    private void saveUserMoneyDetails(BusinessParam param) {
        String title = "";
        Integer classify = 0;
        String content = "";
        if (Objects.equals(1, param.getBusinessType())) {
            title = "开通VIP";
            classify = 1;
            content = "开通VIP";
        } else if (Objects.equals(2, param.getBusinessType())) {
            title = "解锁付费群组";
            classify = 2;
            content = "解锁付费群组";
        }

        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(param.getUserId());
        userMoneyDetails.setTitle(title);
        userMoneyDetails.setClassify(classify);
        userMoneyDetails.setType(2);
        userMoneyDetails.setState(2);
        userMoneyDetails.setMoney(param.getAmount());
        userMoneyDetails.setContent(content);
        iUserMoneyDetailsService.save(userMoneyDetails);
    }
}
