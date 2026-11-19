package com.sqx.app.wallet.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.app.wallet.domain.entity.UserMoney;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface UserMoneyDao extends BaseMapper<UserMoney> {

    void decr(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    void incr(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    UserMoney selectByUserId(@Param("userId") Long userId);
}
