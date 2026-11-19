package com.sqx.app.lygl.background.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBackgroundPageBo extends PageParam {

    @Schema(description = "用户id", hidden = true)
    private Long userId;
}
