package com.sqx.app.lygl.scenicSpot.info.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author Administrator
* @description 针对表【travel_scenic_spot(景点信息)】的数据库操作Mapper
* @createDate 2024-10-10 10:28:58
* @Entity com.sqx.lvyougonglve.scenicSpot.entity.TravelScenicSpot
*/
public interface TravelScenicSpotMapper extends BaseMapper<TravelScenicSpot> {

    IPage<ScenicSpotVo> searchScenicSpot(Page<TravelScenicSpot> initPage, @Param("userId") Long userId, @Param("keyword") String keyword);
}




