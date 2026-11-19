package com.sqx.app.lygl.like.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.LikeSaveBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_like(用户点赞)】的数据库操作Service
* @createDate 2024-10-11 10:41:32
*/
public interface TravelLikeService {

    Result<?> saveInfo(LikeSaveBo likeSaveBo);

    Result<?> pageInfo(LikePageBo likePageBo);

    IPage<MomentLikePageVo> momentLikePage(MomentLikePageBo momentLikePageBo);
}
