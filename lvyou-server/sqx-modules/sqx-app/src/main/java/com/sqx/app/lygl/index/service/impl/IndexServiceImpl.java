package com.sqx.app.lygl.index.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.base.pay.enums.PlatformEnum;
import com.sqx.app.base.utils.DateUtil;
import com.sqx.app.base.utils.model.StartAndEndTime;
import com.sqx.app.lygl.concert.db.service.ITravelConcertService;
import com.sqx.app.lygl.concert.entity.TravelConcert;
import com.sqx.app.lygl.follow.db.service.ITravelFollowService;
import com.sqx.app.lygl.follow.entity.TravelFollow;
import com.sqx.app.lygl.group.groupInfo.db.service.ITravelGroupInfoService;
import com.sqx.app.lygl.group.groupInfo.entity.TravelGroupInfo;
import com.sqx.app.lygl.index.domain.bo.LineStatisticsBO;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.vo.MyDataVo;
import com.sqx.app.lygl.index.domain.bo.StatisticsBo;
import com.sqx.app.lygl.index.domain.vo.*;
import com.sqx.app.lygl.index.service.IndexService;
import com.sqx.app.lygl.like.db.service.ITravelLikeService;
import com.sqx.app.lygl.like.entity.TravelLike;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import com.sqx.app.lygl.payDetails.enums.PayDetailState;
import com.sqx.app.lygl.payDetails.service.IPayDetailsService;
import com.sqx.app.lygl.router.baseInfo.db.service.ITravelBaseInfoService;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.shop.shopInfo.db.service.ITravelShopService;
import com.sqx.common.mybatis.utils.PageUtil;
import com.sqx.domain.db.entity.UserEntity;
import com.sqx.domain.db.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2025/10/10
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private ITravelLikeService iTravelLikeService;
    @Resource
    private ITravelFollowService iTravelFollowService;
    @Resource
    private ITravelMomentsService iTravelMomentsService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IPayDetailsService iPayDetailsService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelConcertService iTravelConcertService;
    @Resource
    private ITravelShopService iTravelShopService;
    @Resource
    private ITravelBaseInfoService iTravelBaseInfoService;
    @Resource
    private ITravelGroupInfoService iTravelGroupInfoService;

    @Override
    public MyDataVo myData(Long userId) {
        Long fansCount = countFans(userId);
        Long followCount = countFollow(userId);
        Long likerCount = countLiker(userId);

        MyDataVo vo = new MyDataVo();
        vo.setFansCount(fansCount);
        vo.setFollowCount(followCount);
        vo.setLikerCount(likerCount);
        return vo;
    }

    @Override
    public IndexStatisticsVo indexStatistics() {
        Long totalUserCount = getTotalUserCount();
        Long todayUserCount = getTodayUserCount();
        Long monthUserCount = getMonthUserCount();
        Long yearUserCount = getYearUserCount();

        Long scenicSpotCount = iTravelScenicSpotService.count();
        Long shopCount = iTravelShopService.count();
        Long baseInfoCount = iTravelBaseInfoService.count();

        IndexStatisticsVo vo = new IndexStatisticsVo();
        vo.setTotalUserCount(totalUserCount);
        vo.setTodayUserCount(todayUserCount);
        vo.setMonthUserCount(monthUserCount);
        vo.setYearUserCount(yearUserCount);
        vo.setScenicSpotCount(scenicSpotCount);
        vo.setShopCount(shopCount);
        vo.setBaseInfoCount(baseInfoCount);
        return vo;
    }

    private Long getYearUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.year();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getMonthUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.month();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getTodayUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.today();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getTotalUserCount() {
        return iUserService.count();
    }

    @Override
    public UserStatisticsVo userStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange();

        Long totalUserCount = getUserCount(statisticsBo.getStartTime(), statisticsBo.getEndTime());

        UserStatisticsVo vo = new UserStatisticsVo();
        vo.setTotalCount(totalUserCount);
        return vo;
    }

    private Long getUserCount(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startTime, endTime);

        return iUserService.count(queryWrapper);
    }

    @Override
    public PayStatisticsVo payStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange();
        LambdaQueryWrapper<PayDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PayDetails::getState, PayDetailState.SUCCESS.code())
                .between(PayDetails::getPayTime, statisticsBo.getStartTime(), statisticsBo.getEndTime());
        List<PayDetails> payDetailsList = iPayDetailsService.list(queryWrapper);

        Map<Integer, BigDecimal> map = new HashMap<>();
        for (PlatformEnum value : PlatformEnum.values()) {
            map.put(Integer.valueOf(value.getCode()), BigDecimal.ZERO);
        }

        for (PayDetails payDetails : payDetailsList) {
            if (map.containsKey(payDetails.getPlatform())) {
                map.merge(payDetails.getPlatform(), payDetails.getAmount(), BigDecimal::add);
            }
        }

        List<PayStatisticsVo.DataInfo> dataInfos = new ArrayList<>();
        map.forEach((k, v) -> {
            PayStatisticsVo.DataInfo dataInfo = new PayStatisticsVo.DataInfo();
            dataInfo.setPlatform(k);
            dataInfo.setAmount(v);

            dataInfos.add(dataInfo);
        });

        PayStatisticsVo vo = new PayStatisticsVo();
        vo.setDataInfoList(dataInfos);
        return vo;
    }

    @Override
    public IPage<PayDetailsVo> payDetailPage(PayDetailsPageBo payDetailsPageBo) {
        Page<PayDetails> initPage = PageUtil.initPage(payDetailsPageBo.getPage(), payDetailsPageBo.getLimit());

        IPage<PayDetailsVo> page = iPayDetailsService.pageInfo(initPage, payDetailsPageBo);
        return page;
    }

    @Override
    public UserLineVO userLine(LineStatisticsBO lineStatisticsBO) {
        List<LocalDate> dateRanges = DateUtil.dateRange(lineStatisticsBO.getStartTime(), lineStatisticsBO.getEndTime());

        List<Map<String, Object>> maps = iUserService.getBaseMapper().selectMaps(new QueryWrapper<UserEntity>()
                .select("DATE_FORMAT(create_time, '%Y-%m-%d') as dayStr", "COUNT(*) as cnt")
                .ge("create_time", lineStatisticsBO.getStartTime().atStartOfDay())
                .le("create_time", lineStatisticsBO.getEndTime().atTime(LocalTime.MAX))
                .groupBy("DATE_FORMAT(create_time, '%Y-%m-%d')")
                .orderByAsc("dayStr")
        );

        List<UserLineVO.UserLine> userLines = maps.stream().map(map -> {
            UserLineVO.UserLine dto = new UserLineVO.UserLine();
            dto.setDayStr((String) map.get("dayStr"));
            dto.setCnt(Math.toIntExact((Long) map.get("cnt")));
            return dto;
        }).collect(Collectors.toList());

        Map<String, Integer> userLineMap = userLines.stream()
                .collect(Collectors.toMap(
                        UserLineVO.UserLine::getDayStr,
                        UserLineVO.UserLine::getCnt
                ));

        List<String> xdates = new ArrayList<>();
        List<Integer> seriesData = new ArrayList<>();
        for (LocalDate date : dateRanges) {
            xdates.add(date.toString());
            seriesData.add(userLineMap.getOrDefault(date.toString(), 0));
        }

        UserLineVO vo = new UserLineVO();
        vo.setXDate(xdates);
        vo.setSeries(seriesData);
        return vo;
    }

    @Override
    public List<ConcertStatisticsVO> concertStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange();

        return iTravelConcertService.countByLocaltion(statisticsBo.getStartTime(), statisticsBo.getEndTime());
    }

    @Override
    public MomentStatisticsVO momentStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange();

        Long waitAuditCount = countMoments(0, statisticsBo.getStartTime(), statisticsBo.getEndTime());
        Long auditCount = countMoments(1, statisticsBo.getStartTime(), statisticsBo.getEndTime());
        Long unAuditCount = countMoments(2, statisticsBo.getStartTime(), statisticsBo.getEndTime());

        MomentStatisticsVO vo = new MomentStatisticsVO();
        vo.setWaitAuditCount(waitAuditCount);
        vo.setAuditCount(auditCount);
        vo.setUnAuditCount(unAuditCount);
        return vo;
    }

    @Override
    public ExamineStatisticsVO examineStatistics() {

        long momentCount = iTravelMomentsService.count(new LambdaQueryWrapper<TravelMoments>()
                .eq(TravelMoments::getAuditStatus, 0)
        );

        long groupCount = iTravelGroupInfoService.count(new LambdaQueryWrapper<TravelGroupInfo>()
                .eq(TravelGroupInfo::getAuditStatus, 0)
        );

        ExamineStatisticsVO vo = new ExamineStatisticsVO();
        vo.setMomentCount(momentCount);
        vo.setGroupCount(groupCount);
        return vo;
    }

    private Long countMoments(Integer status, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<TravelMoments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TravelMoments::getAuditStatus, status);
        queryWrapper.between(TravelMoments::getCreateTime, startTime, endTime);

        return iTravelMomentsService.count(queryWrapper);
    }

    private Long countLiker(Long userId) {
        // 查询所有动态
        LambdaQueryWrapper<TravelMoments> momentsQueryWrapper = new LambdaQueryWrapper<>();
        momentsQueryWrapper.eq(TravelMoments::getUserId, userId);
        List<TravelMoments> moments = iTravelMomentsService.list(momentsQueryWrapper);
        List<Long> momentIds = moments.stream().map(TravelMoments::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(momentIds)) {
            return 0L;
        }

        LambdaQueryWrapper<TravelLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TravelLike::getDataId, momentIds);
        return iTravelLikeService.count(queryWrapper);
    }

    private Long countFollow(Long userId) {
        LambdaQueryWrapper<TravelFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TravelFollow::getUserId, userId);

        return iTravelFollowService.count(queryWrapper);
    }

    private Long countFans(Long userId) {
        LambdaQueryWrapper<TravelFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TravelFollow::getTargetUserId, userId);

        return iTravelFollowService.count(queryWrapper);
    }
}
