package com.sqx.app.lygl.like.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.LikeVo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.app.lygl.like.entity.TravelLike;

/**
* @author Administrator
* @description 针对表【travel_like(用户点赞)】的数据库操作Service
* @createDate 2024-10-11 10:41:32
*/
public interface ITravelLikeService extends IService<TravelLike> {

    IPage<LikeVo> pageInfo(LikePageBo likePageBo);

    IPage<MomentLikePageVo> momentLikePage(Page<TravelLike> initPage, MomentLikePageBo momentLikePageBo);
}
