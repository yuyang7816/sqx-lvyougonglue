package com.sqx.app.lygl.group.groupUser.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserInfoBo;
import com.sqx.app.lygl.group.groupUser.domain.bo.GroupUserPageBo;
import com.sqx.app.lygl.group.groupUser.domain.vo.GroupUserVo;
import com.sqx.app.lygl.group.groupUser.entity.TravelGroupUser;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_group_user(群聊用户信息)】的数据库操作Mapper
* @createDate 2024-10-12 13:16:01
* @Entity com.sqx.lvyougonglve.groupUser.entity.TravelGroupUser
*/
public interface TravelGroupUserMapper extends BaseMapper<TravelGroupUser> {

    IPage<GroupUserVo> pageInfo(Page<TravelGroupUser> initPage, @Param("param") GroupUserPageBo groupUserPageBo);

    GroupUserVo info(@Param("param") GroupUserInfoBo groupUserInfoBo);
}




