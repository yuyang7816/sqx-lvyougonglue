package com.sqx.app.lygl.moments.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.moments.db.mapper.TravelMomentsMapper;
import com.sqx.app.lygl.moments.db.service.ITravelMomentsService;
import com.sqx.app.lygl.moments.domain.bo.MomentsPageBo;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.moments.service.TravelMomentsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
* @author Administrator
* @description 针对表【travel_moments(动态)】的数据库操作Service实现
* @createDate 2024-10-10 14:59:26
*/
@Service
public class ITravelMomentsServiceImpl extends ServiceImpl<TravelMomentsMapper, TravelMoments>
    implements ITravelMomentsService {

    @Resource
    private TravelMomentsMapper travelMomentsMapper;

    @Override
    public IPage<MomentsVo> pageInfo(Page<TravelMoments> initPage, MomentsPageBo momentsPageBo) {
        return travelMomentsMapper.pageInfo(initPage, momentsPageBo);
    }

    @Override
    public MomentsVo info(Long id) {
        return travelMomentsMapper.info(id);
    }

    @Override
    public List<MomentsVo> listByMomentsId(Long userId, List<Long> dataIdList) {
        if (CollectionUtils.isEmpty(dataIdList)) {
            return Collections.emptyList();
        }

        return travelMomentsMapper.listByMomentsId(userId, dataIdList);
    }

    @Override
    public IPage<MomentsVo> searchMoments(Page<TravelMoments> initPage, Long userId, String keyword) {
        return travelMomentsMapper.searchMoments(initPage, userId, keyword);
    }
}




