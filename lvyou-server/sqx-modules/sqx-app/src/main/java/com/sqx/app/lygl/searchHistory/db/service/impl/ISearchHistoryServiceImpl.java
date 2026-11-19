package com.sqx.app.lygl.searchHistory.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.searchHistory.db.mapper.SearchHistoryMapper;
import com.sqx.app.lygl.searchHistory.db.service.ISearchHistoryService;
import com.sqx.app.lygl.searchHistory.domain.entity.SearchHistory;
import com.sqx.app.lygl.searchHistory.domain.vo.SearchHistoryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 用户搜索历史 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-09-17 13:23:01
 */
@Service
public class ISearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public List<SearchHistoryVo> hot() {
        return searchHistoryMapper.hot();
    }
}
