package com.sqx.app.lygl.group.groupInfo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.group.groupInfo.domain.bo.GroupInfoPageBo;
import com.sqx.app.lygl.group.groupInfo.domain.bo.GroupOwnerBo;
import com.sqx.app.lygl.group.groupInfo.domain.vo.GroupInfoVo;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_group_info(群信息表)】的数据库操作Mapper
* @createDate 2024-10-12 10:32:58
* @Entity com.sqx.lvyougonglve.groupInfo.entity.TravelGroupInfo
*/
public interface TravelGroupInfoMapper extends BaseMapper<TravelGroupInfo> {

    IPage<GroupInfoVo> pageInfo(Page<TravelGroupInfo> initPage, @Param("param") GroupInfoPageBo groupInfoPageBo);

    /**
     * 增加群聊人数
     * @param groupInfoId 群聊Id
     * @param num 人数
     */
    void addPeople(@Param("groupInfoId") Long groupInfoId, @Param("num") Integer num);

    /**
     * 增加群聊浏览次数
     * @param groupInfoId 群聊Id
     * @param num 人数
     */
    void addViews(@Param("groupInfoId") Long groupInfoId, @Param("num") Integer num);

    GroupInfoVo getInfo(@Param("id") Long id);

    IPage<GroupInfoVo> owner(Page<TravelGroupInfo> initPage, @Param("param") GroupOwnerBo groupOwnerBo);

    IPage<GroupInfoVo> joinList(Page<TravelGroupInfo> initPage, @Param("param") GroupOwnerBo groupOwnerBo);
}




