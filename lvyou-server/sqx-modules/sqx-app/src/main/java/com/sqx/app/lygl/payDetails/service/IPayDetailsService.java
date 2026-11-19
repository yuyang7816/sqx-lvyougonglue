package com.sqx.app.lygl.payDetails.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.vo.PayDetailsVo;
import com.sqx.app.lygl.payDetails.entity.PayDetails;

/**
* @author Administrator
* @description 针对表【pay_details(用户支付详情)】的数据库操作Service
* @createDate 2024-10-12 13:40:24
*/
public interface IPayDetailsService extends IService<PayDetails> {

    IPage<PayDetailsVo> pageInfo(Page<PayDetails> initPage, PayDetailsPageBo payDetailsPageBo);
}
