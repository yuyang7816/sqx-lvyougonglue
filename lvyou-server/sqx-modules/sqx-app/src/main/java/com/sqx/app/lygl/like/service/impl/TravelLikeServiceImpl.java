package com.sqx.app.lygl.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.domain.bo.LikePageBo;
import com.sqx.app.lygl.like.domain.bo.LikeSaveBo;
import com.sqx.app.lygl.like.domain.bo.MomentLikePageBo;
import com.sqx.app.lygl.like.domain.vo.LikeVo;
import com.sqx.app.lygl.like.domain.vo.MomentLikePageVo;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.like.enums.LikeTypeEnum;
import com.sqx.app.lygl.like.service.TravelLikeService;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.common.core.domain.Result;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_like(用户点赞)】的数据库操作Service实现
* @createDate 2024-10-11 10:41:32
*/
@Service
public class TravelLikeServiceImpl implements TravelLikeService {

    @Resource
    private ITravelLikeService iTravelLikeService;
    @Resource
    private ITravelMomentsService momentsService;
    @Resource
    private ITravelCommentService commentService;

    @Override
    public Result<?> saveInfo(LikeSaveBo likeSaveBo) {
        QueryWrapper<TravelLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TravelLike::getUserId, likeSaveBo.getUserId())
                .eq(TravelLike::getType, likeSaveBo.getType())
                .eq(TravelLike::getDataId, likeSaveBo.getDataId());
        boolean exists = iTravelLikeService.exists(queryWrapper);

        if (exists) {
            iTravelLikeService.remove(queryWrapper);
        } else {
            TravelLike travelLike = new TravelLike();
            BeanUtils.copyProperties(likeSaveBo, travelLike);

            iTravelLikeService.save(travelLike);
        }

        return Result.success();
    }

    @Override
    public Result<?> pageInfo(LikePageBo likePageBo) {
        IPage<LikeVo> likeIPage = iTravelLikeService.pageInfo(likePageBo);
        if (CollectionUtils.isEmpty(likeIPage.getRecords())) {
            return Result.success(likeIPage);
        }

        List<LikeVo> voList = likeIPage.getRecords();
        List<Long> dataIdList = voList.stream().map(LikeVo::getDataId).collect(Collectors.toList());
        if (Objects.equals(likePageBo.getType(), LikeTypeEnum.MOMENTS.code())) {
            List<MomentsVo> momentsList = momentsService.listByMomentsId(likePageBo.getUserId(), dataIdList);
            Map<Long, TravelMoments> momentsMap = momentsList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (LikeVo vo : voList) {
                if (momentsMap.containsKey(vo.getDataId())) {
                    vo.setMoments(momentsMap.get(vo.getDataId()));
                }
            }
        }

        if (Objects.equals(likePageBo.getType(), LikeTypeEnum.COMMENT.code())) {
            List<TravelComment> commentList = commentService.list(new QueryWrapper<TravelComment>()
                    .lambda()
                    .in(TravelComment::getId, dataIdList)
            );

            Map<Long, TravelComment> commentMap = commentList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);
            for (LikeVo vo : voList) {
                if (commentMap.containsKey(vo.getDataId())) {
                    vo.setComment(commentMap.get(vo.getDataId()));
                }
            }
        }

        IPage<LikeVo> iPage = PageUtil.copyPage(likeIPage, voList);
        return Result.success(iPage);
    }

    @Override
    public IPage<MomentLikePageVo> momentLikePage(MomentLikePageBo momentLikePageBo) {
        Page<TravelLike> initPage = PageUtil.initPage(momentLikePageBo.getPage(), momentLikePageBo.getLimit());

        return iTravelLikeService.momentLikePage(initPage, momentLikePageBo);
    }
}




