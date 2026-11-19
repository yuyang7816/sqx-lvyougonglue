package com.sqx.app.base.pay.business.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sqx.app.base.enums.AppError;
import com.sqx.app.base.pay.business.service.CallbackService;
import com.sqx.app.base.pay.enums.BusinessType;
import com.sqx.app.lygl.group.groupInfo.db.service.ITravelGroupInfoService;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import com.sqx.app.lygl.group.groupInfo.enums.GroupStatus;
import com.sqx.app.lygl.group.groupUser.db.service.ITravelGroupUserService;
import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import com.sqx.app.lygl.group.groupUser.enums.GroupUserStatus;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import com.sqx.app.lygl.payDetails.enums.PayDetailState;
import com.sqx.app.lygl.payDetails.service.IPayDetailsService;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.exception.SqxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/21
 *
 * 回调业务
 */
@Slf4j
@Service
public class CallbackServiceImpl implements CallbackService {
    @Resource
    private IPayDetailsService iPayDetailsService;
    @Resource
    private ITravelGroupInfoService iTravelGroupInfoService;
    @Resource
    private ITravelGroupUserService iTravelGroupUserService;

    @Override
    public void successProcess(String orderNo, String tradeNo) {
        log.info("回调业务处理");
        PayDetails payDetails = getPayDetail(orderNo);
        if (Objects.isNull(payDetails)) {
            throw new BizException(AppError.PAY_NOT_FOUND);
        }
        if (!Objects.equals(PayDetailState.WAIT_PAY.code(), payDetails.getState())) {
            throw new BizException(AppError.PAY_STATUS_ERROR);
        }

        if (Objects.equals(BusinessType.VIP.getCode(), payDetails.getBusinessType())) {
            //currentProxy.vipOrderHandler(orderNo, tradeNo);
        } else if (Objects.equals(BusinessType.JOIN_GROUP.getCode(), payDetails.getBusinessType())) {
            joinGroupHandler(orderNo, tradeNo);
        } else {
            log.error("不支持的业务类型： {}", payDetails.getBusinessType());
        }
    }

    /**
     * 用户加入付费群聊回调
     * @param orderNo 订单号
     * @param tradeNo 第三方支付流水号
     */
    private void joinGroupHandler(String orderNo, String tradeNo) {
        PayDetails payDetails = iPayDetailsService.getOne(new LambdaQueryWrapper<PayDetails>().eq(PayDetails::getOrderNo, orderNo));
        if (Objects.isNull(payDetails)) {
            throw new SqxException("订单信息不存在");
        }

        if (!Objects.equals(PayDetailState.WAIT_PAY.code(), payDetails.getState())) {
            throw new BizException(AppError.PAY_STATUS_ERROR);
        }

        String extend = payDetails.getExtend();
        JSONObject extendJson = JSONObject.parseObject(extend);
        // 添加用户到群聊
        TravelGroupInfo groupInfo = iTravelGroupInfoService.getById(extendJson.getLong("groupId"));
        if (Objects.isNull(groupInfo)) {
            throw new SqxException("群聊不存在");
        }
        if (!Objects.equals(groupInfo.getStatus(), GroupStatus.ENABLE.code())) {
            throw new SqxException("不能加入群聊，群聊状态: " + GroupStatus.getDescByCode(groupInfo.getStatus()));
        }

        TravelGroupUser groupUser = new TravelGroupUser();
        groupUser.setUserId(payDetails.getUserId());
        groupUser.setGroupInfoId(extendJson.getLong("groupId"));
        groupUser.setStatus(GroupUserStatus.ENABLE.code());

        LocalDateTime expireTime = LocalDateTime.now().plusDays(groupInfo.getExpiredDays());
        groupUser.setExpireTime(expireTime);
        iTravelGroupUserService.save(groupUser);

        // 更新支付详情
        updatePayDetails(orderNo, tradeNo, PayDetailState.SUCCESS.code());
    }

    public void updatePayDetails(String orderNo, String tradeNo, Integer status) {
        UpdateWrapper<PayDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(PayDetails::getOrderNo, orderNo)
                .set(PayDetails::getTradeNo, tradeNo)
                .set(PayDetails::getState, status);

        iPayDetailsService.update(new PayDetails(), updateWrapper);
    }

    private PayDetails getPayDetail(String orderNo) {
        QueryWrapper<PayDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayDetails::getOrderNo, orderNo);

        PayDetails payDetails = iPayDetailsService.getOne(queryWrapper);

        return payDetails;
    }
}
