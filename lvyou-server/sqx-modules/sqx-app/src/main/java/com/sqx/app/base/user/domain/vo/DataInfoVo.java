package com.sqx.app.base.user.domain.vo;

import lombok.Data;

/**
 * @author chengming
 * @since 2024/10/12
 */
@Data
public class DataInfoVo {

    // 粉丝
    private Long userFansNum;

    // 关注
    private Long userLikeNum;

    // 获赞
    private Long userReceivedLike;
}
