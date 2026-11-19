package com.sqx.app.base.user.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/11/6
 */
@Data
public class UserPageBo extends PageParam {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户名")
    private String userName;
}
