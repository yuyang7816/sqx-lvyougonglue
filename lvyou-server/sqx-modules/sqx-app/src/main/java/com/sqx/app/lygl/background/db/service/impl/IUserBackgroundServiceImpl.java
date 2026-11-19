package com.sqx.app.lygl.background.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.background.db.mapper.UserBackgroundMapper;
import com.sqx.app.lygl.background.db.service.IUserBackgroundService;
import com.sqx.app.lygl.background.domain.entity.UserBackground;
import org.springframework.stereotype.Service;

/**
 * 用户背景图 服务实现类
 *
 * @author sqx
 * @since 2025-06-09 15:15:07
 */
@Service
public class IUserBackgroundServiceImpl extends ServiceImpl<UserBackgroundMapper, UserBackground> implements IUserBackgroundService {

    @Override
    public UserBackground getByUserId(Long userId) {
        LambdaQueryWrapper<UserBackground> queryWrapper = new LambdaQueryWrapper<UserBackground>()
                .eq(UserBackground::getUserId, userId);

        return this.getOne(queryWrapper);
    }
}
