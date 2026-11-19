package com.sqx.app.lygl.group.groupType.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class GroupTypeSaveBo {

    /**
     * 名称
     */
    @Schema(description = "类型名称", required = true, example = "示例名称")
    @NotBlank(message = "类型名称不能为空")
    private String name;

    /**
     * 备注
     */
    @Schema(description = "备注信息")
    private String remark;

    /**
     * 最小人数
     */
    @Schema(description = "最小人数")
    private Integer minPeopleNum;

    /**
     * 最大人数
     */
    @Schema(description = "允许的最大人数", example = "100")
    private Integer maxPeopleNum;

    /**
     * 最大金额
     */
    @Schema(description = "允许的最大金额", example = "1000.00")
    private BigDecimal maxAmount;

    /**
     * 最小金额
     */
    @Schema(description = "允许的最小金额", example = "10.00")
    private BigDecimal minAmount;
}
