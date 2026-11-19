package com.sqx.domain.db.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.db.entity.UserEntity;
import com.sqx.domain.db.mapper.UserDao;
import com.sqx.domain.db.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * 用户
 *
 * @author fang
 * @date 2021/2/27
 */

@Service
public class IUserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements IUserService {

    @Override
    public UserEntity getByOpenId(String openid) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getOpenId, openid);

        return this.getOne(queryWrapper);
    }

    @Override
    public UserEntity getByInvitationCode(String superiorInvitationCode) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getInvitationCode, superiorInvitationCode);

        return this.getOne(queryWrapper);
    }

    @Override
    public UserEntity getByPhone(String phone) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getPhone, phone);

        return this.getOne(queryWrapper);
    }
}
