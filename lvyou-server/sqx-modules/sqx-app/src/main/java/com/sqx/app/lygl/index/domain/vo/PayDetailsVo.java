package com.sqx.app.lygl.index.domain.vo;

import com.sqx.app.lygl.payDetails.entity.PayDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/11/6
 */
@Data
public class PayDetailsVo extends PayDetails {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;
}
