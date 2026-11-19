package com.sqx.app.lygl.background.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.enums.AppError;
import com.sqx.app.lygl.background.db.service.IUserBackgroundService;
import com.sqx.app.lygl.background.domain.bo.*;
import com.sqx.app.lygl.background.domain.entity.UserBackground;
import com.sqx.app.lygl.background.domain.vo.UserBackgroundVo;
import com.sqx.app.lygl.background.service.UserBackgroundService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/6/9
 */
@Service
public class UserBackgroundServiceImpl implements UserBackgroundService {

    @Resource
    private IUserBackgroundService iUserBackgroundService;

    @Override
    public Result<Void> saveInfo(UserBackgroundSaveBo userBackgroundSaveBo) {
        UserBackground userBackground = new UserBackground();
        BeanUtils.copyProperties(userBackgroundSaveBo, userBackground);
        userBackground.setUserId(0L);

        iUserBackgroundService.save(userBackground);
        return Result.success();
    }

    @Override
    public Result<IPage<UserBackgroundVo>> pageInfo(UserBackgroundPageBo userBackgroundPageBo) {
        Page<UserBackground> initPage = PageUtil.initPage(userBackgroundPageBo.getPage(), userBackgroundPageBo.getLimit());

        QueryWrapper<UserBackground> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(UserBackground::getCreateTime);

        IPage<UserBackground> iPage = iUserBackgroundService.page(initPage, queryWrapper);
        List<UserBackgroundVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (UserBackground record : iPage.getRecords()) {
                UserBackgroundVo vo = new UserBackgroundVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        IPage<UserBackgroundVo> voIPage = PageUtil.copyPage(iPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result<UserBackgroundVo> info(UserBackgroundInfoBo userBackgroundInfoBo) {
        UserBackground userBackground = iUserBackgroundService.getById(userBackgroundInfoBo.getId());
        if (Objects.isNull(userBackground)) {
            throw new BizException(AppError.INFO_NOT_FOUND);
        }

        UserBackgroundVo vo = new UserBackgroundVo();
        BeanUtils.copyProperties(userBackground, vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> updateInfo(UserBackgroundUpdateBo userBackgroundUpdateBo) {
        UserBackground userBackground = new UserBackground();
        BeanUtils.copyProperties(userBackgroundUpdateBo, userBackground);

        iUserBackgroundService.updateById(userBackground);
        return Result.success();
    }

    @Override
    public Result<Void> delete(UserBackgroundInfoBo userBackgroundInfoBo) {
        iUserBackgroundService.removeById(userBackgroundInfoBo.getId());
        return Result.success();
    }

    @Override
    public Result<List<UserBackgroundVo>> list() {
        QueryWrapper<UserBackground> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(UserBackground::getCreateTime);

        List<UserBackground> userBackgroundList = iUserBackgroundService.list(queryWrapper);
        List<UserBackgroundVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userBackgroundList)) {
            for (UserBackground record : userBackgroundList) {
                UserBackgroundVo vo = new UserBackgroundVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        return Result.success(voList);
    }

    @Override
    public Result<Void> bind(UserBackgroundBindBo userBackgroundBindBo) {
        UserBackground userBackground = iUserBackgroundService.getByUserId(userBackgroundBindBo.getUerId());
        if (Objects.isNull(userBackground)) {
            // 绑定
            UserBackground background = new UserBackground();
            background.setUserId(userBackgroundBindBo.getUerId());
            background.setImgUrl(userBackgroundBindBo.getImgUrl());

            iUserBackgroundService.save(background);
        } else {
            // 修改
            iUserBackgroundService.update(new UserBackground(), new LambdaUpdateWrapper<UserBackground>()
                    .eq(UserBackground::getUserId, userBackgroundBindBo.getUerId())
                    .set(UserBackground::getImgUrl, userBackgroundBindBo.getImgUrl())
            );
        }

        return Result.success();
    }
}
