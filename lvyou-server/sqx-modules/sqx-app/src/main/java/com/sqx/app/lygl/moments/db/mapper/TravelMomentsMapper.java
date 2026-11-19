package com.sqx.app.lygl.moments.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.moments.domain.bo.MomentsPageBo;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author Administrator
* @description 针对表【travel_moments(动态)】的数据库操作Mapper
* @createDate 2024-10-10 14:59:26
* @Entity com.sqx.lvyougonglve.moments.entity.TravelMoments
*/
public interface TravelMomentsMapper extends BaseMapper<TravelMoments> {

    IPage<MomentsVo> pageInfo(Page<TravelMoments> initPage, @Param("param") MomentsPageBo momentsPageBo);

    MomentsVo info(@Param("id") Long id);

    List<MomentsVo> listByMomentsId(@Param("userId") Long userId, @Param("dataIdList") List<Long> dataIdList);

    IPage<MomentsVo> searchMoments(Page<TravelMoments> initPage, @Param("userId") Long userId, @Param("keyword") String keyword);
}




