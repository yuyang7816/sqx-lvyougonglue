package com.sqx.app.lygl.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.collect.db.service.ITravelCollectService;
import com.sqx.app.lygl.collect.entity.TravelCollect;
import com.sqx.app.lygl.collect.enums.CollectTypeEnum;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.entity.TravelComment;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.like.enums.LikeTypeEnum;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.domain.bo.*;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.moments.enums.AuditStatus;
import com.sqx.app.lygl.moments.service.TravelMomentsService;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【travel_moments(动态)】的数据库操作Service实现
* @createDate 2024-10-10 14:59:26
*/
@Service
public class TravelMomentsServiceImpl implements TravelMomentsService {

    @Resource
    private ITravelMomentsService iTravelMomentsService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelLikeService iTravelLikeService;
    @Resource
    private ITravelCollectService iTravelCollectService;
    @Resource
    private ITravelCommentService iTravelCommentService;

    @Override
    public Result<?> saveInfo(MomentsSaveBo momentsSaveBo) {
        TravelMoments moments = new TravelMoments();
        BeanUtils.copyProperties(momentsSaveBo, moments);

        moments.setViews(0);
        moments.setAuditStatus(AuditStatus.PENDING.getCode());
        iTravelMomentsService.save(moments);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(Long userId, MomentsPageBo momentsPageBo) {
        Page<TravelMoments> initPage = PageUtil.initPage(momentsPageBo.getPage(), momentsPageBo.getLimit());

        IPage<MomentsVo> momentsIPage = iTravelMomentsService.pageInfo(initPage, momentsPageBo);
        for (MomentsVo momentsVo : momentsIPage.getRecords()) {
            // 点赞数
            Long likeNum = getLikeNum(momentsVo.getId());
            // 收藏数
            Long collectNum = getCollectNum(momentsVo.getId());
            // 评论数
            Long commentNum = getCommentNum(momentsVo.getId());

            momentsVo.setLikeNum(likeNum);
            momentsVo.setCollectNum(collectNum);
            momentsVo.setCommentNum(commentNum);

            // 是否点赞
            boolean isLike = checkLike(userId, momentsVo.getId());
            momentsVo.setIsLike(isLike);

            // 是否收藏
            boolean isCollect = checkCollect(userId, momentsVo.getId());
            momentsVo.setIsCollect(isCollect);
        }

        return Result.success(momentsIPage);
    }

    private boolean checkCollect(Long userId, Long momentsId) {
        if (Objects.isNull(userId)) {
            return false;
        }

        return iTravelCollectService.exists(new LambdaQueryWrapper<TravelCollect>()
                .eq(TravelCollect::getDataId, momentsId)
                .eq(TravelCollect::getType, 1)
                .eq(TravelCollect::getUserId, userId));
    }

    private boolean checkLike(Long userId, Long momentsId) {
        if (Objects.isNull(userId)) {
            return false;
        }

        return iTravelLikeService.exists(new LambdaQueryWrapper<TravelLike>()
                .eq(TravelLike::getDataId, momentsId)
                .eq(TravelLike::getType, 1)
                .eq(TravelLike::getUserId, userId));
    }

    /**
     * 动态评论数
     * @param momentsId 动态ID
     * @return
     */
    private Long getCommentNum(Long momentsId) {
        return iTravelCommentService.count(new QueryWrapper<TravelComment>()
                .lambda()
                .eq(TravelComment::getDataId, momentsId)
                .eq(TravelComment::getType, 1)
        );
    }

    /**
     * 动态收藏数
     * @param momentsId 动态ID
     * @return
     */
    private Long getCollectNum(Long momentsId) {
        return iTravelCollectService.count(new QueryWrapper<TravelCollect>()
                .lambda()
                .eq(TravelCollect::getDataId, momentsId)
                .eq(TravelCollect::getType, CollectTypeEnum.MOMENTS.code())
        );
    }

    /**
     * 动态点赞数
     * @param momentsId 动态ID
     * @return
     */
    private Long getLikeNum(Long momentsId) {
        return iTravelLikeService.count(new QueryWrapper<TravelLike>()
                .lambda()
                .eq(TravelLike::getDataId, momentsId)
                .eq(TravelLike::getType, LikeTypeEnum.MOMENTS.code())
        );
    }

    @Override
    public Result<?> info(MomentsInfoBo momentsInfoBo) {
        MomentsVo vo = iTravelMomentsService.info(momentsInfoBo.getId());
        if (Objects.isNull(vo)) {
            throw new SqxException("动态信息不存在");
        }

        if (Objects.nonNull(vo.getScenicSpotId())) {
            // 获取景点信息
            ScenicSpotVo scenicSpotVo = getScenicSpotInfo(vo.getScenicSpotId());
            vo.setScenicSpotVo(scenicSpotVo);
        }

        // 点赞数
        Long likeNum = getLikeNum(vo.getId());
        // 收藏数
        Long collectNum = getCollectNum(vo.getId());
        // 评论数
        Long commentNum = getCommentNum(vo.getId());

        vo.setLikeNum(likeNum);
        vo.setCollectNum(collectNum);
        vo.setCommentNum(commentNum);

        if (Objects.nonNull(momentsInfoBo.getUserId())) {
            // 是否点赞
            boolean isLike = checkLike(momentsInfoBo.getUserId(), vo.getId());
            vo.setIsLike(isLike);

            // 是否收藏
            boolean isCollect = checkCollect(momentsInfoBo.getUserId(), vo.getId());
            vo.setIsCollect(isCollect);
        }


        // 每次访问详情浏览量 + 1
        UpdateWrapper<TravelMoments> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(TravelMoments::getId, momentsInfoBo.getId())
                .setSql("views = views + 1");
        iTravelMomentsService.update(new TravelMoments(), updateWrapper);

        return Result.success(vo);
    }

    @Override
    public Result<?> updateInfo(MomentsUpdateBo momentsUpdateBo) {
        TravelMoments moments = new TravelMoments();
        BeanUtils.copyProperties(momentsUpdateBo, moments);
        iTravelMomentsService.updateById(moments);

        return Result.success();
    }

    @Override
    public Result<?> delete(MomentsInfoBo momentsInfoBo) {
        iTravelMomentsService.removeById(momentsInfoBo.getId());
        return Result.success();
    }

    @Override
    public void audit(MomentsAuditBo momentsAuditBo) {
        TravelMoments travelMoments = iTravelMomentsService.getById(momentsAuditBo.getId());
        if (Objects.isNull(travelMoments)) {
            throw new BizException("动态信息不存在");
        }

        travelMoments.setAuditStatus(momentsAuditBo.getAuditStatus());
        travelMoments.setAuditReason(momentsAuditBo.getAuditReason());

        iTravelMomentsService.updateById(travelMoments);
    }

    /**
     * 获取景点信息
     * @param scenicSpotId 景点id
     * @return
     */
    private ScenicSpotVo getScenicSpotInfo(Long scenicSpotId) {
        TravelScenicSpot scenicSpot = iTravelScenicSpotService.getById(scenicSpotId);

        ScenicSpotVo vo = new ScenicSpotVo();
        BeanUtils.copyProperties(scenicSpot, vo);

        return vo;
    }
}




