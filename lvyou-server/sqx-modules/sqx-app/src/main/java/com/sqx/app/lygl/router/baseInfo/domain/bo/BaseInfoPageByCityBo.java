package com.sqx.app.lygl.router.baseInfo.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author chengming
 * @since 2025/12/4
 */
@Data
public class BaseInfoPageByCityBo extends PageParam {

    @Schema(description = "城市")
    private String city;
}
