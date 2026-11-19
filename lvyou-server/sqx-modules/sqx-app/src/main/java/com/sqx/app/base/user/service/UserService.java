package com.sqx.app.base.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.base.user.domain.bo.ForgetPasswordBO;
import com.sqx.app.base.user.domain.bo.ListUserBO;
import com.sqx.app.base.user.domain.bo.UpdateUserBO;
import com.sqx.app.base.user.domain.bo.UserPageBo;
import com.sqx.app.base.user.domain.vo.UserVo;
import com.sqx.common.core.domain.Result;

/**
 * @author chengming
 * @since 2025/4/24
 */
public interface UserService {
    Result<UserVo> info(Long userId);

    Result<Void> forgetPassword(ForgetPasswordBO forgetPasswordBO);

    IPage<UserVo> list(ListUserBO listUserBO);

    void update(UpdateUserBO updateUserBO);

    Result<?> data(Long userId);

    Result<?> userData(Long currUserId, Long userId);

    Result<IPage<UserVo>> userPage(UserPageBo userPageBo);
}
