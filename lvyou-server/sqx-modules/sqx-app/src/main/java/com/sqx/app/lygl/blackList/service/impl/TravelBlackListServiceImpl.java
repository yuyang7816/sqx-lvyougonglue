package com.sqx.app.lygl.blackList.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.blackList.db.mapper.TravelBlackListMapper;
import com.sqx.app.lygl.blackList.db.service.ITravelBlackListService;
import com.sqx.app.lygl.blackList.domain.bo.BlackListInfoBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListPageBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListSaveBo;
import com.sqx.app.lygl.blackList.domain.vo.BlackListVo;
import com.sqx.app.lygl.blackList.entity.TravelBlackList;
import com.sqx.app.lygl.blackList.service.TravelBlackListService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/6/10
 */
@Service
public class TravelBlackListServiceImpl implements TravelBlackListService {

    @Resource
    private TravelBlackListMapper blackListMapper;
    @Resource
    private ITravelBlackListService iTravelBlackListService;

    @Override
    public Result<?> saveInfo(BlackListSaveBo blackListSaveBo) {
        if (Objects.equals(blackListSaveBo.getBlackUserId(), blackListSaveBo.getUserId())) {
            throw new SqxException("不能拉黑自己");
        }

        TravelBlackList blackList = new TravelBlackList();
        BeanUtils.copyProperties(blackListSaveBo, blackList);

        iTravelBlackListService.save(blackList);
        return Result.success();
    }

    @Override
    public Result<?> pageInfo(BlackListPageBo blackListPageBo) {
        Page<TravelBlackList> initPage = PageUtil.initPage(blackListPageBo.getPage(), blackListPageBo.getLimit());

        IPage<BlackListVo> blackListVoIPage = blackListMapper.pageInfo(initPage, blackListPageBo);
        return Result.success(blackListVoIPage);
    }

    @Override
    public Result<?> info(BlackListInfoBo blackListInfoBo) {
        BlackListVo vo = blackListMapper.info(blackListInfoBo.getId());
        if (Objects.isNull(vo)) {
            throw new SqxException("黑名单信息不存在");
        }

        return Result.success(vo);
    }

    @Override
    public Result<?> delete(BlackListInfoBo blackListInfoBo) {
        iTravelBlackListService.removeById(blackListInfoBo.getId());
        return Result.success();
    }
}
