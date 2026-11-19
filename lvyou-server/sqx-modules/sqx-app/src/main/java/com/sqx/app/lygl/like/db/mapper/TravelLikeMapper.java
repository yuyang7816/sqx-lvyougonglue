package com.sqx.app.lygl.like.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.LikeVo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.app.lygl.like.entity.TravelLike;
import org.apache.ibatis.annotations.Param;


/**
* @author Administrator
* @description 针对表【travel_like(用户点赞)】的数据库操作Mapper
* @createDate 2024-10-11 10:41:32
* @Entity com.sqx.lvyougonglve.like.entity.TravelLike
*/
public interface TravelLikeMapper extends BaseMapper<TravelLike> {

    IPage<LikeVo> pageInfo(Page<TravelLike> initPage, @Param("param") LikePageBo likePageBo);

    IPage<MomentLikePageVo> momentLikePage(Page<TravelLike> initPage, @Param("param") MomentLikePageBo momentLikePageBo);
}




