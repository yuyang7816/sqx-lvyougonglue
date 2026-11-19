package com.sqx.app.lygl.like.domain.vo;

import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/11/17
 */
@Data
public class MomentLikePageVo extends TravelLike {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer sex;

}
