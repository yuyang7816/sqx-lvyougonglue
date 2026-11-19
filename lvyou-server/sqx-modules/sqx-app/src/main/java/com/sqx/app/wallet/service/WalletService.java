package com.sqx.app.wallet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.wallet.domain.bo.FundDetailBo;
import com.sqx.app.wallet.domain.bo.UpdateUserMoneyBo;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import com.sqx.app.wallet.domain.vo.UserMoneyVo;


/**
 * @author chengming
 * @since 2024/7/23
 */
public interface WalletService {

    UserMoneyVo info(Long userId);

    IPage<UserMoneyDetails> fundDetail(FundDetailBo fundDetailBo);

    void updateUserMoney(UpdateUserMoneyBo updateUserMoneyBo);
}
