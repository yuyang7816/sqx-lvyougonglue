package com.sqx.app.lygl.blackList.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlackListPageBo extends PageParam {

    /**
     * 用户id
     */
    @Schema(description = "用户id", hidden = true)
    private Long userId;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;

    /**
     * 被拉黑的用户id
     */
    @Schema(description = "被拉黑的用户id")
    private Long blackUserId;

    /**
     * 被拉黑的用户名称
     */
    @Schema(description = "被拉黑的用户名称")
    private String blackUserName;
}
