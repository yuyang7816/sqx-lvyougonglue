package com.sqx.app.lygl.like.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.like.db.mapper.TravelLikeMapper;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.LikeVo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_like(用户点赞)】的数据库操作Service实现
* @createDate 2024-10-11 10:41:32
*/
@Service
public class ITravelLikeServiceImpl extends ServiceImpl<TravelLikeMapper, TravelLike>
    implements ITravelLikeService {

    @Resource
    private TravelLikeMapper travelLikeMapper;

    @Override
    public IPage<LikeVo> pageInfo(LikePageBo likePageBo) {
        Page<TravelLike> initPage = PageUtil.initPage(likePageBo.getPage(), likePageBo.getLimit());

        return travelLikeMapper.pageInfo(initPage, likePageBo);
    }

    @Override
    public IPage<MomentLikePageVo> momentLikePage(Page<TravelLike> initPage, MomentLikePageBo momentLikePageBo) {
        return travelLikeMapper.momentLikePage(initPage, momentLikePageBo);
    }
}




