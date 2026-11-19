package com.sqx.app.lygl.comment.domain.vo;

import com.sqx.app.lygl.comment.entity.TravelComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/11/17
 */
@Data
public class MomentCommentPageVo extends TravelComment {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer sex;
}
