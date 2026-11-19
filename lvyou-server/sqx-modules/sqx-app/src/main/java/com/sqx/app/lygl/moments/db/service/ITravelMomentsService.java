package com.sqx.app.lygl.moments.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.moments.domain.bo.MomentsPageBo;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;

import java.util.List;

/**
* @author Administrator
* @description 针对表【travel_moments(动态)】的数据库操作Service
* @createDate 2024-10-10 14:59:26
*/
public interface ITravelMomentsService extends IService<TravelMoments> {

    IPage<MomentsVo> pageInfo(Page<TravelMoments> initPage, MomentsPageBo momentsPageBo);

    MomentsVo info(Long id);

    List<MomentsVo> listByMomentsId(Long userId, List<Long> dataIdList);

    IPage<MomentsVo> searchMoments(Page<TravelMoments> initPage, Long userId, String keyword);
}
