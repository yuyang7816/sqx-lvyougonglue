package com.sqx.app.lygl.blackList.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.blackList.domain.bo.BlackListPageBo;
import com.sqx.app.lygl.blackList.domain.vo.BlackListVo;
import com.sqx.app.lygl.blackList.entity.TravelBlackList;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_black_list(黑名单)】的数据库操作Mapper
* @createDate 2024-10-14 10:57:33
* @Entity com.sqx.lvyougonglve.blackList.entity.TravelBlackList
*/
public interface TravelBlackListMapper extends BaseMapper<TravelBlackList> {

    IPage<BlackListVo> pageInfo(Page<TravelBlackList> initPage, @Param("param") BlackListPageBo blackListPageBo);

    BlackListVo info(Long id);
}




