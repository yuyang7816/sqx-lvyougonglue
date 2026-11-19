package com.sqx.app.base.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.enums.AppError;
import com.sqx.app.base.user.domain.bo.ForgetPasswordBO;
import com.sqx.app.base.user.domain.bo.ListUserBO;
import com.sqx.app.base.user.domain.bo.UpdateUserBO;
import com.sqx.app.base.user.domain.bo.UserPageBo;
import com.sqx.app.base.user.domain.vo.DataInfoVo;
import com.sqx.app.base.user.domain.vo.UserIndexVo;
import com.sqx.app.base.user.domain.vo.UserVo;
import com.sqx.app.base.user.service.UserService;
import com.sqx.app.base.verifyCode.service.VerifyCodeService;
import com.sqx.app.lygl.background.db.service.IUserBackgroundService;
import com.sqx.app.lygl.background.domain.entity.UserBackground;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.app.lygl.follow.db.service.ITravelFollowService;
import com.sqx.app.lygl.follow.entity.TravelFollow;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.wallet.db.service.IUserMoneyService;
import com.sqx.app.wallet.domain.entity.UserMoney;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.mybatis.utils.PageUtil;
import com.sqx.domain.db.entity.UserEntity;
import com.sqx.domain.db.service.ISysConfigService;
import com.sqx.domain.db.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Resource
    private ITravelFollowService iTravelFollowService;
    @Resource
    private ITravelLikeService iTravelLikeService;
    @Resource
    private ITravelCommentService iTravelCommentService;
    @Resource
    private IUserBackgroundService iUserBackgroundService;
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private IUserMoneyService iUserMoneyService;

    @Override
    public Result<UserVo> info(Long userId) {
        UserEntity userEntity = iUserService.getById(userId);
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.USER_NOT_FOUND);
        }

        UserBackground userBackground = iUserBackgroundService.getByUserId(userId);
        if (Objects.nonNull(userBackground)) {
            userEntity.setUserBackgroundImage(userBackground.getImgUrl());
        }

        // 邀请人数是否达到免费解锁
        boolean isUnlocked = checkInviteNumUnLocked(userEntity.getInvitationCode());
        userEntity.setIsUnLocked(isUnlocked);

        UserVo vo = new UserVo();
        BeanUtils.copyProperties(userEntity, vo);

        UserMoney userMoney = iUserMoneyService.getByUserId(userId);
        if (Objects.isNull(userMoney)) {
            throw new BizException("用户钱包不存在");
        }

        vo.setMoney(userMoney.getMoney());
        return Result.success(vo);
    }

    private boolean checkInviteNumUnLocked(String invitationCode) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getSuperiorInvitationCode, invitationCode);

        long count = iUserService.count(queryWrapper);
        String unlockGroupNum = iSysConfigService.getConfigValue("biz_unlock_group_num");

        return count >= Long.parseLong(unlockGroupNum);
    }

    @Override
    public Result<Void> forgetPassword(ForgetPasswordBO forgetPasswordBO) {
        UserEntity userEntity = iUserService.getById(forgetPasswordBO.getUserId());
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.USER_NOT_FOUND);
        }

        if (!verifyCodeService.verify(userEntity.getPhone(), forgetPasswordBO.getCode())) {
            throw new BizException(AppError.VERIFY_CODE_ERROR);
        }

        iUserService.update(new UserEntity(), new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserId, forgetPasswordBO.getUserId())
                .set(UserEntity::getPassword, DigestUtils.md5Hex(forgetPasswordBO.getPassword()))
        );

        return Result.success();
    }

    @Override
    public IPage<UserVo> list(ListUserBO listUserBO) {
        Page<UserEntity> initPage = PageUtil.initPage(listUserBO.getPage(), listUserBO.getLimit());

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(listUserBO.getPhone()), UserEntity::getPhone, listUserBO.getPhone())
                .eq(StringUtils.isNotBlank(listUserBO.getUsername()), UserEntity::getUserName, listUserBO.getUsername())
                .eq(Objects.nonNull(listUserBO.getStatus()), UserEntity::getStatus, listUserBO.getStatus())
                .eq(Objects.nonNull(listUserBO.getUserType()), UserEntity::getUserType, listUserBO.getUserType())
                .orderByDesc(UserEntity::getCreateTime);

        Page<UserEntity> userEntityPage = iUserService.page(initPage, queryWrapper);
        List<UserVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userEntityPage.getRecords())) {
            for (UserEntity user : userEntityPage.getRecords()) {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(user, vo);

                voList.add(vo);
            }
        }

        IPage<UserVo> userVoIPage = PageUtil.copyPage(userEntityPage, voList);
        return userVoIPage;
    }

    @Override
    public void update(UpdateUserBO updateUserBO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(updateUserBO, userEntity);

        iUserService.updateById(userEntity);
    }

    @Override
    public Result<?> data(Long userId) {
        // 粉丝
        Long userFansNum = countUserFans(userId);

        // 关注
        Long userLikeNum = countUserLike(userId);

        // 获赞
        Long userReceivedLikeNum = countUserReceivedLike(userId);

        DataInfoVo vo = new DataInfoVo();
        vo.setUserFansNum(userFansNum);
        vo.setUserLikeNum(userLikeNum);
        vo.setUserReceivedLike(userReceivedLikeNum);

        return Result.success(vo);
    }

    @Override
    public Result<?> userData(Long currUserId, Long userId) {
        UserEntity userEntity = iUserService.getById(userId);
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.USER_NOT_FOUND);
        }

        Long userFans = countUserFans(userId);
        Long userFollow = countUserLike(userId);
        Boolean isFollow = checkFollow(currUserId, userId);

        UserIndexVo vo = new UserIndexVo();
        vo.setUserEntity(userEntity);
        vo.setFansCount(userFans);
        vo.setLikeCount(userFollow);
        vo.setIsFollow(isFollow);
        return Result.success(vo);
    }

    @Override
    public Result<IPage<UserVo>> userPage(UserPageBo userPageBo) {
        Page<UserEntity> initPage = PageUtil.initPage(userPageBo.getPage(), userPageBo.getLimit());

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(userPageBo.getPhone()), UserEntity::getPhone, userPageBo.getPhone())
                .like(StringUtils.isNotBlank(userPageBo.getUserName()), UserEntity::getUserName, userPageBo.getUserName())
                .orderByDesc(UserEntity::getCreateTime);

        IPage<UserEntity> page = iUserService.page(initPage, queryWrapper);
        List<UserVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            for (UserEntity user : page.getRecords()) {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(user, vo);

                voList.add(vo);
            }
        }

        IPage<UserVo> userVoIPage = PageUtil.copyPage(page, voList);
        return Result.success(userVoIPage);
    }

    /**
     * 是否关注
     * @param currUserId 用户id
     * @param userId 被关注用户id
     * @return
     */
    private Boolean checkFollow(Long currUserId, Long userId) {
        return iTravelFollowService.exists(new LambdaQueryWrapper<TravelFollow>()
                .eq(TravelFollow::getUserId, currUserId)
                .eq(TravelFollow::getTargetUserId, userId)
        );
    }

    /**
     * 获赞数
     */
    private Long countUserReceivedLike(Long userId) {
        // 获取用户所有动态（不统计评论的点赞数）
        List<TravelComment> commentList = iTravelCommentService.list(new QueryWrapper<TravelComment>()
                .lambda()
                .eq(TravelComment::getUserId, userId)
        );
        if (CollectionUtils.isEmpty(commentList)){
            return 0L;
        }

        List<Long> commentIdList = commentList.stream().map(TravelComment::getId).collect(Collectors.toList());

        // 统计获赞数
        QueryWrapper<TravelLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(TravelLike::getDataId, commentIdList);
        return iTravelLikeService.count(queryWrapper);
    }

    private Long countUserLike(Long userId) {
        QueryWrapper<TravelFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelFollow::getUserId, userId);

        return iTravelFollowService.count(queryWrapper);
    }

    private Long countUserFans(Long userId) {
        QueryWrapper<TravelFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelFollow::getTargetUserId, userId);

        return iTravelFollowService.count(queryWrapper);
    }
}
