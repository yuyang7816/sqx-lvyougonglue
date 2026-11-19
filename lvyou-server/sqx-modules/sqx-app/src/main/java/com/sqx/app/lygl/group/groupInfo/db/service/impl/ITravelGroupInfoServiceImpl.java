package com.sqx.app.lygl.group.groupInfo.db.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.group.groupInfo.db.mapper.TravelGroupInfoMapper;
import com.sqx.app.lygl.group.groupInfo.db.service.ITravelGroupInfoService;
import com.sqx.app.lygl.group.groupInfo.domain.bo.GroupOwnerBo;
import com.sqx.app.lygl.group.groupInfo.domain.vo.GroupInfoVo;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_group_info(群信息表)】的数据库操作Service实现
* @createDate 2024-10-12 10:32:58
*/
@Service
public class ITravelGroupInfoServiceImpl extends ServiceImpl<TravelGroupInfoMapper, TravelGroupInfo>
    implements ITravelGroupInfoService {

    @Resource
    private TravelGroupInfoMapper travelGroupInfoMapper;

    @Override
    public GroupInfoVo getInfo(Long id) {
        return travelGroupInfoMapper.getInfo(id);
    }

    @Override
    public void addPeople(Long id, int num) {
        travelGroupInfoMapper.addPeople(id, num);
    }

    @Override
    public IPage<GroupInfoVo> owner(Page<TravelGroupInfo> initPage, GroupOwnerBo groupOwnerBo) {
        return travelGroupInfoMapper.owner(initPage, groupOwnerBo);
    }

    @Override
    public IPage<GroupInfoVo> joinList(Page<TravelGroupInfo> initPage, GroupOwnerBo groupOwnerBo) {
        return travelGroupInfoMapper.joinList(initPage, groupOwnerBo);
    }
}




