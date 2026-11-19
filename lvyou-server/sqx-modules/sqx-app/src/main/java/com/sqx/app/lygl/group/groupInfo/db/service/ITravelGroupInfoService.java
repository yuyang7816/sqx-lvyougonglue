package com.sqx.app.lygl.group.groupInfo.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.group.groupInfo.domain.bo.GroupOwnerBo;
import com.sqx.app.lygl.group.groupInfo.domain.vo.GroupInfoVo;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;

import javax.validation.constraints.NotNull;


/**
* @author Administrator
* @description 针对表【travel_group_info(群信息表)】的数据库操作Service
* @createDate 2024-10-12 10:32:58
*/
public interface ITravelGroupInfoService extends IService<TravelGroupInfo> {

    GroupInfoVo getInfo(Long id);

    void addPeople(Long id, int num);

    IPage<GroupInfoVo> owner(Page<TravelGroupInfo> initPage, GroupOwnerBo groupOwnerBo);

    IPage<GroupInfoVo> joinList(Page<TravelGroupInfo> initPage, GroupOwnerBo groupOwnerBo);
}
