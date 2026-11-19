package com.sqx.app.lygl.payDetails.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.vo.PayDetailsVo;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import com.sqx.app.lygl.payDetails.mapper.PayDetailsMapper;
import com.sqx.app.lygl.payDetails.service.IPayDetailsService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【pay_details(用户支付详情)】的数据库操作Service实现
* @createDate 2024-10-12 13:40:24
*/
@Service
public class IPayDetailsServiceImpl extends ServiceImpl<PayDetailsMapper, PayDetails>
    implements IPayDetailsService {

    @Override
    public IPage<PayDetailsVo> pageInfo(Page<PayDetails> initPage, PayDetailsPageBo payDetailsPageBo) {
        return this.baseMapper.pageInfo(initPage, payDetailsPageBo);
    }
}




