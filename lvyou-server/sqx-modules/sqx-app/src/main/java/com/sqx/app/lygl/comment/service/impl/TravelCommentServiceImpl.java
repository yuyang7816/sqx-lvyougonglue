package com.sqx.app.lygl.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.comment.db.mapper.TravelCommentMapper;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.domain.bo.*;
import com.sqx.app.lygl.comment.domain.vo.CommentVo;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.app.lygl.comment.service.TravelCommentService;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import com.sqx.domain.db.service.IMessageInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_comment(用户评论)】的数据库操作Service实现
* @createDate 2024-10-11 13:22:26
*/
@Service
public class TravelCommentServiceImpl implements TravelCommentService {

    @Resource
    private TravelCommentMapper commentMapper;
    @Resource
    private ITravelCommentService iTravelCommentService;
    @Resource
    private ITravelLikeService iTravelLikeService;
    @Resource
    private IMessageInfoService iMessageInfoService;
    @Resource
    private ITravelMomentsService iTravelMomentsService;

    @Override
    public Result<?> saveInfo(CommentSaveBo commentSaveBo) {
        TravelComment comment = new TravelComment();
        BeanUtils.copyProperties(commentSaveBo, comment);

        iTravelCommentService.save(comment);
        // 通知用户评论消息
        sendCommentMessage(commentSaveBo);

        return Result.success();
    }

    private void sendCommentMessage(CommentSaveBo commentSaveBo) {
        TravelMoments travelMoments = iTravelMomentsService.getById(commentSaveBo.getDataId());
        if (Objects.isNull(travelMoments)) {
            return ;
        }

        iMessageInfoService.saveComment(travelMoments.getUserId(), commentSaveBo.getContent(), commentSaveBo.getDataId());
    }

    @Override
    public Result<?> pageInfo(CommentPageBo commentPageBo) {
        Page<TravelComment> initPage = PageUtil.initPage(commentPageBo.getPage(), commentPageBo.getLimit());

        IPage<CommentVo> commentVoIPage = commentMapper.pageInfo(initPage, commentPageBo);

        if (CollectionUtils.isNotEmpty(commentVoIPage.getRecords())) {
            List<Long> commentIds = commentVoIPage.getRecords().stream().map(CommentVo::getId).collect(Collectors.toList());

            List<TravelLike> travelLikes = iTravelLikeService.list(new LambdaQueryWrapper<TravelLike>()
                    .in(TravelLike::getDataId, commentIds)
                    .eq(TravelLike::getType, 2)
            );

            List<TravelLike> userLikes = travelLikes.stream()
                    .filter(item -> Objects.equals(item.getUserId(), commentPageBo.getUserId()))
                    .collect(Collectors.toList());

            Map<Long, TravelLike> userLikeMap = userLikes.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

            Map<Long, List<TravelLike>> travelLikeMap = travelLikes.stream().collect(Collectors.groupingBy(TravelLike::getDataId));

            for (CommentVo vo : commentVoIPage.getRecords()) {
                vo.setIsLike(userLikeMap.containsKey(vo.getId()));

                if (travelLikeMap.containsKey(vo.getId())) {
                    vo.setLikeCount((long) travelLikeMap.get(vo.getId()).size());
                } else {
                    vo.setLikeCount(0L);
                }

            }
        }

        return Result.success(commentVoIPage);
    }

    @Override
    public Result<?> info(CommentInfoBo commentInfoBo) {
        TravelComment comment = iTravelCommentService.getById(commentInfoBo.getId());
        if (Objects.isNull(comment)) {
            throw new SqxException("评论不存在");
        }

        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(comment, vo);
        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(CommentUpdateBo commentUpdateBo) {
        TravelComment comment = new TravelComment();
        BeanUtils.copyProperties(commentUpdateBo, comment);

        iTravelCommentService.updateById(comment);
        return Result.success();
    }

    @Override
    public Result<?> delete(CommentInfoBo commentInfoBo) {
        iTravelCommentService.removeById(commentInfoBo.getId());
        return Result.success();
    }

    @Override
    public IPage<MomentCommentPageVo> momentCommentPage(MomentCommentPageBo momentCommentPageBo) {
        Page<TravelComment> initPage = PageUtil.initPage(momentCommentPageBo.getPage(), momentCommentPageBo.getLimit());

        return iTravelCommentService.momentCommentPage(initPage, momentCommentPageBo);
    }
}




