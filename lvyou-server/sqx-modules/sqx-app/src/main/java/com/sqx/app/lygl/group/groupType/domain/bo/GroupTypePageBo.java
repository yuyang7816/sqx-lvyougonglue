package com.sqx.app.lygl.group.groupType.domain.bo;


import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupTypePageBo extends PageParam {

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 状态（0正常 1禁用）
     */
    @Schema(description = "状态（0正常 1禁用）")
    private Integer status;
}
