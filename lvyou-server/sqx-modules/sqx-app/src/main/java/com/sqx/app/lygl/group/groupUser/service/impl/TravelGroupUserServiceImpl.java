package com.sqx.app.lygl.group.groupUser.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.group.groupUser.db.mapper.TravelGroupUserMapper;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserInfoBo;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserPageBo;
import com.sqx.app.lygl.group.groupUser.domain.vo.GroupUserVo;
import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import com.sqx.app.lygl.group.groupUser.service.TravelGroupUserService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【travel_group_user(群聊用户信息)】的数据库操作Service实现
* @createDate 2024-10-12 13:16:01
*/
@Service
public class TravelGroupUserServiceImpl implements TravelGroupUserService {

    @Resource
    private TravelGroupUserMapper groupUserMapper;

    @Override
    public Result<?> pageInfo(GroupUserPageBo groupUserPageBo) {
        Page<TravelGroupUser> initPage = PageUtil.initPage(groupUserPageBo.getPage(), groupUserPageBo.getLimit());

        IPage<GroupUserVo> groupUserVoIPage = groupUserMapper.pageInfo(initPage, groupUserPageBo);
        return Result.success(groupUserVoIPage);
    }

    @Override
    public Result<?> info(GroupUserInfoBo groupUserInfoBo) {
        GroupUserVo vo = groupUserMapper.info(groupUserInfoBo);
        if (Objects.isNull(vo)) {
            throw new SqxException("群聊用户不存在");
        }

        return Result.success(vo);
    }
}




