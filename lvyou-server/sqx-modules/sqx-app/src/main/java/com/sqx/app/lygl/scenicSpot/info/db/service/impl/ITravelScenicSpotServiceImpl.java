package com.sqx.app.lygl.scenicSpot.info.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.scenicSpot.info.db.mapper.TravelScenicSpotMapper;
import com.sqx.app.lygl.scenicSpot.info.db.service.ITravelScenicSpotService;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
* @author Administrator
* @description 针对表【travel_scenic_spot(景点信息)】的数据库操作Service实现
* @createDate 2024-10-10 10:28:58
*/
@Service
public class ITravelScenicSpotServiceImpl extends ServiceImpl<TravelScenicSpotMapper, TravelScenicSpot>
    implements ITravelScenicSpotService {

    @Resource
    private TravelScenicSpotMapper travelScenicSpotMapper;

    @Override
    public IPage<ScenicSpotVo> searchScenicSpot(Page<TravelScenicSpot> initPage, Long userId, String keyword) {
        return travelScenicSpotMapper.searchScenicSpot(initPage, userId, keyword);
    }
}




