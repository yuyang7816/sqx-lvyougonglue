package com.sqx.app.lygl.follow.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.follow.domain.bo.FollowPageBo;
import com.sqx.app.lygl.follow.domain.vo.FollowVo;
import com.sqx.app.lygl.follow.entity.TravelFollow;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_follow(用户关注)】的数据库操作Mapper
* @createDate 2024-10-12 15:12:55
* @Entity com.sqx.lvyougonglve.follow.entity.TravelFollow
*/
public interface TravelFollowMapper extends BaseMapper<TravelFollow> {

    IPage<FollowVo> pageInfo(Page<TravelFollow> initPage, @Param("param") FollowPageBo followPageBo);
}




