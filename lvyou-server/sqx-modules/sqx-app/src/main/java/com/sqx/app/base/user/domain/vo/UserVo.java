package com.sqx.app.base.user.domain.vo;

import com.sqx.domain.db.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/11/10
 */
@Data
public class UserVo extends UserEntity {

    @Schema(description = "金额")
    private BigDecimal money;
}
