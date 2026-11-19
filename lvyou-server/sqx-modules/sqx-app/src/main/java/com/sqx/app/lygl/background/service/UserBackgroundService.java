package com.sqx.app.lygl.background.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.background.domain.bo.*;
import com.sqx.app.lygl.background.domain.vo.UserBackgroundVo;
import com.sqx.common.core.domain.Result;

import java.util.List;

/**
 * @author chengming
 * @since 2025/6/9
 */
public interface UserBackgroundService {

    Result<Void> saveInfo(UserBackgroundSaveBo userBackgroundSaveBo);

    Result<IPage<UserBackgroundVo>> pageInfo(UserBackgroundPageBo userBackgroundPageBo);

    Result<UserBackgroundVo> info(UserBackgroundInfoBo userBackgroundInfoBo);

    Result<Void> updateInfo(UserBackgroundUpdateBo userBackgroundUpdateBo);

    Result<Void> delete(UserBackgroundInfoBo userBackgroundInfoBo);

    Result<List<UserBackgroundVo>> list();

    Result<Void> bind(UserBackgroundBindBo userBackgroundBindBo);
}
