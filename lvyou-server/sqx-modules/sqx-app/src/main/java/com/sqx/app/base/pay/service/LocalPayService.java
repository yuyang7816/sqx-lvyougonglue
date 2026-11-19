package com.sqx.app.base.pay.service;

import com.sqx.app.base.pay.domain.BusinessParam;
import com.sqx.common.core.domain.Result;

/**
 * @author chengming
 * @since 2025/7/9
 */
public interface LocalPayService {
    Result<String> pay(BusinessParam param);
}
