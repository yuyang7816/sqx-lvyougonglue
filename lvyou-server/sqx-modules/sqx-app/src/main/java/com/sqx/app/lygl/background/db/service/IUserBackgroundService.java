package com.sqx.app.lygl.background.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.background.domain.entity.UserBackground;


/**
 * 用户背景图 服务类
 *
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
public interface IUserBackgroundService extends IService<UserBackground> {

    UserBackground getByUserId(Long userId);
}
