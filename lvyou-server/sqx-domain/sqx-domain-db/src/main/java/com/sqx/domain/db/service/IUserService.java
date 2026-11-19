package com.sqx.domain.db.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.db.entity.UserEntity;


/**
 * 用户
 *
 * @author fang
 * @date 2021/2/27
 */
public interface IUserService extends IService<UserEntity> {

    UserEntity getByOpenId(String openid);

    UserEntity getByInvitationCode(String superiorInvitationCode);

    UserEntity getByPhone(String phone);
}
