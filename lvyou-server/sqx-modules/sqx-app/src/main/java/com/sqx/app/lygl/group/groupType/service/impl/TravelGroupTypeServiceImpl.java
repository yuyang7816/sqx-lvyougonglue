package com.sqx.app.lygl.group.groupType.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.group.groupType.db.service.ITravelGroupTypeService;
import com.sqx.app.lygl.group.groupType.domain.bo.*;
import com.sqx.app.lygl.group.groupType.entity.TravelGroupType;
import com.sqx.app.lygl.group.groupType.enums.GroupTypeStatus;
import com.sqx.app.lygl.group.groupType.service.TravelGroupTypeService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【travel_group_type(群类型表)】的数据库操作Service实现
* @createDate 2024-10-12 10:11:07
*/
@Service
public class TravelGroupTypeServiceImpl implements TravelGroupTypeService {

    @Resource
    private ITravelGroupTypeService iTravelGroupTypeService;

    @Override
    public Result<?> saveInfo(GroupTypeSaveBo groupTypeSaveBo) {
        TravelGroupType groupType = new TravelGroupType();
        BeanUtils.copyProperties(groupTypeSaveBo, groupType);

        groupType.setStatus(GroupTypeStatus.ENABLE.code());
        iTravelGroupTypeService.save(groupType);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(GroupTypePageBo groupTypePageBo) {
        Page<TravelGroupType> initPage = PageUtil.initPage(groupTypePageBo.getPage(), groupTypePageBo.getLimit());

        QueryWrapper<TravelGroupType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(groupTypePageBo.getName()), TravelGroupType::getName, groupTypePageBo.getName())
                .eq(Objects.nonNull(groupTypePageBo.getStatus()), TravelGroupType::getStatus, groupTypePageBo.getStatus())
                .orderByDesc(TravelGroupType::getCreateTime);

        IPage<TravelGroupType> groupTypeIPage = iTravelGroupTypeService.page(initPage, queryWrapper);
        return Result.success(groupTypeIPage);
    }

    @Override
    public Result<?> info(GroupTypeInfoBo groupTypeInfoBo) {
        TravelGroupType groupType = iTravelGroupTypeService.getById(groupTypeInfoBo.getId());
        if (Objects.isNull(groupType)) {
            throw new SqxException("群聊类型不存在");
        }

        return Result.success(groupType);
    }

    @Override
    public Result<?> updateInfo(GroupTypeUpdateBo groupTypeUpdateBo) {
        TravelGroupType groupType = new TravelGroupType();
        BeanUtils.copyProperties(groupTypeUpdateBo, groupType);

        iTravelGroupTypeService.updateById(groupType);
        return Result.success();
    }

    @Override
    public Result<?> delete(GroupTypeInfoBo groupTypeInfoBo) {
        iTravelGroupTypeService.removeById(groupTypeInfoBo.getId());
        return Result.success();
    }

    @Override
    public Result<?> updateStatus(GroupTypeUpdateStatusBo groupTypeUpdateStatusBo) {
        UpdateWrapper<TravelGroupType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelGroupType::getId, groupTypeUpdateStatusBo.getId())
                .set(TravelGroupType::getStatus, groupTypeUpdateStatusBo.getStatus());

        iTravelGroupTypeService.update(new TravelGroupType(), updateWrapper);
        return Result.success();
    }
}




