package com.sqx.app.lygl.searchHistory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.router.baseInfo.db.service.ITravelBaseInfoService;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.app.lygl.scenicSpot.ticket.db.service.ITravelScenicSpotTicketService;
import com.sqx.app.lygl.searchHistory.db.service.ISearchHistoryService;
import com.sqx.app.lygl.searchHistory.domain.bo.*;
import com.sqx.app.lygl.searchHistory.domain.entity.SearchHistory;
import com.sqx.app.lygl.searchHistory.domain.vo.SearchHistoryVo;
import com.sqx.app.lygl.searchHistory.domain.vo.SearchVo;
import com.sqx.app.lygl.searchHistory.enums.SearchType;
import com.sqx.app.lygl.searchHistory.service.SearchHistoryService;
import com.sqx.app.lygl.shop.shopInfo.db.service.ITravelShopService;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 用户搜索历史 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-09-17 13:23:01
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Resource
    private ISearchHistoryService iSearchHistoryService;
    @Resource
    private ITravelShopService iTravelShopService;
    @Resource
    private ITravelScenicSpotService iTravelScenicSpotService;
    @Resource
    private ITravelBaseInfoService iTravelBaseInfoService;
    @Resource
    private ITravelMomentsService iTravelMomentsService;

    @Override
    public void saveInfo(SearchHistorySaveBo searchHistorySaveBo) {
        SearchHistory searchHistory = new SearchHistory();
        BeanUtils.copyProperties(searchHistorySaveBo, searchHistory);

        iSearchHistoryService.save(searchHistory);
    }

    @Override
    public IPage<SearchHistoryVo> pageInfo(SearchHistoryPageBo searchHistoryPageBo) {
        Page<SearchHistory> initPage = PageUtil.initPage(searchHistoryPageBo.getPage(), searchHistoryPageBo.getLimit());

        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(searchHistoryPageBo.getUserId()), SearchHistory::getUserId, searchHistoryPageBo.getUserId())
                .like(StringUtils.isNotBlank(searchHistoryPageBo.getKeyword()), SearchHistory::getKeyword, searchHistoryPageBo.getKeyword())
                .orderByDesc(SearchHistory::getCreateTime);

        IPage<SearchHistory> iPage = iSearchHistoryService.page(initPage, queryWrapper);
        List<SearchHistoryVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (SearchHistory record : iPage.getRecords()) {
                SearchHistoryVo vo = new SearchHistoryVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        IPage<SearchHistoryVo> voIPage = PageUtil.copyPage(iPage, voList);
        return voIPage;
    }

    @Override
    public SearchHistoryVo info(SearchHistoryInfoBo searchHistoryInfoBo) {
        SearchHistory searchHistory = iSearchHistoryService.getById(searchHistoryInfoBo.getId());
        if (Objects.isNull(searchHistory)) {
            throw new BizException("信息不存在");
        }

        SearchHistoryVo vo = new SearchHistoryVo();
        BeanUtils.copyProperties(searchHistory, vo);
        return vo;
    }

    @Override
    public void updateInfo(SearchHistoryUpdateBo searchHistoryUpdateBo) {
        SearchHistory searchHistory = new SearchHistory();
        BeanUtils.copyProperties(searchHistoryUpdateBo, searchHistory);

        iSearchHistoryService.updateById(searchHistory);
    }

    @Override
    public void delete(SearchHistoryInfoBo searchHistoryInfoBo) {
        iSearchHistoryService.removeById(searchHistoryInfoBo.getId());
    }

    @Override
    public List<SearchHistoryVo> listInfo(SearchHistoryListBo searchHistoryListBo) {
        LambdaQueryWrapper<SearchHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Objects.nonNull(searchHistoryListBo.getUserId()), SearchHistory::getUserId, searchHistoryListBo.getUserId())
                .like(StringUtils.isNotBlank(searchHistoryListBo.getKeyword()), SearchHistory::getKeyword, searchHistoryListBo.getKeyword())
                .orderByDesc(SearchHistory::getCreateTime);

        List<SearchHistory> searchHistoryList = iSearchHistoryService.list(queryWrapper);
        List<SearchHistoryVo> voList = new ArrayList<>();
        for (SearchHistory searchHistory : searchHistoryList) {
            SearchHistoryVo vo = new SearchHistoryVo();
            BeanUtils.copyProperties(searchHistory, vo);
            voList.add(vo);
        }

        return voList;
    }

    @Override
    public void clear(Long userId) {
        LambdaQueryWrapper<SearchHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SearchHistory::getUserId, userId);

        iSearchHistoryService.remove(queryWrapper);
    }

    @Override
    public SearchVo search(SearchBo searchBo) {
        SearchVo vo = new SearchVo();

        if (StringUtils.isBlank(searchBo.getKeyword())) {
            return null;
        }

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUserId(searchBo.getUserId());
        searchHistory.setKeyword(searchBo.getKeyword());
        iSearchHistoryService.save(searchHistory);

        if (Objects.equals(searchBo.getType(), SearchType.MOMENTS.getType())) {
            IPage<MomentsVo> momentsVoList = searchMoments(searchBo);
            vo.setMomentsVoList(momentsVoList);
        }

        if (Objects.equals(searchBo.getType(), SearchType.SCENIC_SPOT.getType())) {
            IPage<ScenicSpotVo> scenicSpotVoList = searchScenicSpot(searchBo);
            vo.setScenicSpotVoList(scenicSpotVoList);
        }

        if (Objects.equals(searchBo.getType(), SearchType.ROUTE.getType())) {
            IPage<BaseInfoVo> baseInfoIPage = searchRoute(searchBo);
            vo.setBaseInfoVoList(baseInfoIPage);
        }

        if (Objects.equals(searchBo.getType(), SearchType.SHOP.getType())) {
            IPage<TravelShopVo> shopVoIPage = searchShop(searchBo);
            vo.setShopVoList(shopVoIPage);
        }

        return vo;
    }

    @Override
    public List<SearchHistoryVo> hot() {
        return iSearchHistoryService.hot();
    }

    private IPage<TravelShopVo> searchShop(SearchBo searchBo) {
        Page<TravelShopVo> initPage = PageUtil.initPage(searchBo.getPage(), searchBo.getLimit());

        return iTravelShopService.searchShop(initPage, searchBo.getUserId(), searchBo.getKeyword());
    }

    private IPage<BaseInfoVo> searchRoute(SearchBo searchBo) {
        Page<TravelBaseInfo> initPage = PageUtil.initPage(searchBo.getPage(), searchBo.getLimit());

        return iTravelBaseInfoService.searchRoute(initPage, searchBo.getUserId(), searchBo.getKeyword());
    }

    private IPage<ScenicSpotVo> searchScenicSpot(SearchBo searchBo) {
        Page<TravelScenicSpot> initPage = PageUtil.initPage(searchBo.getPage(), searchBo.getLimit());

        return iTravelScenicSpotService.searchScenicSpot(initPage, searchBo.getUserId(), searchBo.getKeyword());
    }

    private IPage<MomentsVo> searchMoments(SearchBo searchBo) {
        Page<TravelMoments> initPage = PageUtil.initPage(searchBo.getPage(), searchBo.getLimit());

        return iTravelMomentsService.searchMoments(initPage, searchBo.getUserId(), searchBo.getKeyword());
    }
}
