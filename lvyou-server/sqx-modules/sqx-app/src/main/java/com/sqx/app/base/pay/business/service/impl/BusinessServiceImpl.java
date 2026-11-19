package com.sqx.app.base.pay.business.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.app.base.enums.AppError;
import com.sqx.app.base.pay.business.service.BusinessService;
import com.sqx.app.base.pay.domain.BusinessParam;
import com.sqx.app.base.pay.enums.BusinessType;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import com.sqx.app.lygl.payDetails.enums.PayDetailState;
import com.sqx.app.lygl.payDetails.service.IPayDetailsService;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.utils.GenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/21
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private IPayDetailsService iPayDetailsService;

    @Override
    public void process(String orderNo, String platform, BusinessParam param) {
        log.info("支付业务处理");

        if (Objects.equals(Integer.valueOf(param.getBusinessType()), BusinessType.VIP.getCode())) {

        } else if (Objects.equals(Integer.valueOf(param.getBusinessType()), BusinessType.JOIN_GROUP.getCode())) {
            joinGroup(orderNo, platform, param);
        } else {
            throw new BizException(AppError.NOT_SUPPORT_PAY);
        }

    }

    /**
     * 加入付费群组
     * @param orderNo
     * @param platform
     * @param param
     */
    private void joinGroup(String orderNo, String platform, BusinessParam param) {
        if (StringUtils.isBlank(orderNo)) {
            orderNo = GenUtil.genOrderNo();
        }

        PayDetails payDetails = new PayDetails();
        Map<String, Object> extensions = param.getExtensions();
        Long userId = param.getUserId();

        payDetails.setUserId(userId);
        payDetails.setPlatform(Integer.valueOf(platform));
//        payDetails.setTradeType(param.getWxPayParam().getTradeType());
        payDetails.setOrderNo(orderNo);
        payDetails.setAmount(param.getAmount());
        payDetails.setBusinessType(Integer.valueOf(param.getBusinessType()));
        payDetails.setState(PayDetailState.WAIT_PAY.code());
        payDetails.setExtend(JSONObject.toJSONString(extensions));

        iPayDetailsService.save(payDetails);
    }
}
