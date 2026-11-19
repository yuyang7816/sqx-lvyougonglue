package com.sqx.app.lygl.moments.domain.bo;

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
public class MomentsPageBo extends PageParam {

    /**
     * 用户id
     */
    @Schema(description = "用户唯一标识符", example = "12345")
    private Long userId;

    @Schema(description = "用户名")
    private String userName;

    /**
     * 标题
     */
    @Schema(description = "标题信息", example = "美丽的风景")
    private String title;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "审核状态：0-待审核, 1-审核通过, 2-审核拒绝")
    private Integer auditStatus;

    /**
     * 景点id
     */
    @Schema(description = "景点的唯一标识符", example = "67890")
    private Long scenicSpotId;

    @Schema(description = "景点名称")
    private String scenicSpotName;

    @Schema(description = "商户id")
    private Long shopId;
}
