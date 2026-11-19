package com.sqx.app.lygl.payDetails.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.vo.PayDetailsVo;
import com.sqx.app.lygl.payDetails.entity.PayDetails;
import org.apache.ibatis.annotations.Param;


/**
* @author Administrator
* @description 针对表【pay_details(用户支付详情)】的数据库操作Mapper
* @createDate 2024-10-12 13:40:24
* @Entity com.sqx.lvyougonglve.payDetails.entity.PayDetails
*/
public interface PayDetailsMapper extends BaseMapper<PayDetails> {

    IPage<PayDetailsVo> pageInfo(Page<PayDetails> initPage, @Param("param") PayDetailsPageBo payDetailsPageBo);
}




