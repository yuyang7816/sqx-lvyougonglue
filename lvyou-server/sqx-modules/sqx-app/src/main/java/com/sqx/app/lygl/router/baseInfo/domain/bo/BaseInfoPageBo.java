package com.sqx.app.lygl.router.baseInfo.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInfoPageBo extends PageParam {

    private Long currUserId;

    /**
     * 路线标题
     */
    @Schema(description = "路线的标题", example = "最美乡村之旅")
    private String title;

    /**
     * 发布者id
     */
    @Schema(description = "发布者的唯一标识符", example = "12345")
    private Long userId;

    /**
     * 状态 1正常 2禁用
     */
    @Schema(description = "状态值：1表示正常，2表示禁用", example = "1")
    private Integer status;

    @Schema(description = "城市")
    private String city;
}
