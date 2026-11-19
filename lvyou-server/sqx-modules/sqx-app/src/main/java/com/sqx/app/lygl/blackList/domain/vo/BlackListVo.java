package com.sqx.app.lygl.blackList.domain.vo;


import com.sqx.app.lygl.blackList.entity.TravelBlackList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlackListVo extends TravelBlackList {

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 被拉黑的用户名称
     */
    @Schema(description = "被拉黑的用户名称")
    private String blackUserName;

    /**
     * 被拉黑的用户头像
     */
    @Schema(description = "被拉黑的用户头像")
    private String blackUserAvatar;
}
