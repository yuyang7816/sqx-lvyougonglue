package com.sqx.app.lygl.group.groupInfo.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
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
public class GroupInfoPageBo extends PageParam {

    private Long currUserId;

    /**
     * 群所属用户id
     */
    @Schema(description = "群所属用户ID")
    private Long userId;

    /**
     * 群所属用户名称
     */
    @Schema(description = "群所属用户的名称")
    private String userName;

    /**
     * 群类型id
     */
    @Schema(description = "群类型的ID")
    private Long typeId;

    /**
     * 群类型名称
     */
    @Schema(description = "群类型的名称")
    private String typeName;

    /**
     * 群名称
     */
    @Schema(description = "群的名称")
    private String name;

    /**
     * 申请类型（0免费，1付费）
     */
    @Schema(description = "申请类型: 0-免费, 1-付费", allowableValues = {"0", "1"})
    private Integer applyCondition;

    /**
     * 省
     */
    @Schema(description = "所在省份")
    private String province;

    /**
     * 市
     */
    @Schema(description = "所在城市")
    private String city;

    /**
     * 区
     */
    @Schema(description = "所在区县")
    private String region;

    /**
     * 审核状态（0待审核 1审核通过 2审核拒绝）
     */
    private Integer auditStatus;
}
