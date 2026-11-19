package com.sqx.app.lygl.blackList.service;

import com.sqx.app.lygl.blackList.domain.bo.BlackListInfoBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListPageBo;
import com.sqx.app.lygl.blackList.domain.bo.BlackListSaveBo;
import com.sqx.common.core.domain.Result;

/**
 * @author chengming
 * @since 2025/6/10
 */
public interface TravelBlackListService {

    Result<?> saveInfo(BlackListSaveBo blackListSaveBo);

    Result<?> pageInfo(BlackListPageBo blackListPageBo);

    Result<?> info(BlackListInfoBo blackListInfoBo);

    Result<?> delete(BlackListInfoBo blackListInfoBo);
}
