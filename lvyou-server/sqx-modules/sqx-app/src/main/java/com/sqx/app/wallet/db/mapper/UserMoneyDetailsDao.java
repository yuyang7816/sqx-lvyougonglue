package com.sqx.app.wallet.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.app.wallet.domain.entity.UserMoneyDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface UserMoneyDetailsDao extends BaseMapper<UserMoneyDetails> {

    BigDecimal sumTotalRecharge(@Param("userId") Long userId);
}
