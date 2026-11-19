package com.sqx.app.lygl.searchHistory.domain.vo;

import com.alipay.api.domain.BaseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.router.baseInfo.domain.vo.BaseInfoVo;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import com.sqx.app.lygl.shop.shopInfo.domain.vo.TravelShopVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author chengming
 * @since 2025/9/17
 */
@Data
public class SearchVo {

    @Schema(description = "动态")
    private IPage<MomentsVo> momentsVoList;

    @Schema(description = "景点")
    private IPage<ScenicSpotVo> scenicSpotVoList;

    @Schema(description = "路线")
    private IPage<BaseInfoVo> baseInfoVoList;

    @Schema(description = "商户")
    private IPage<TravelShopVo> shopVoList;
}
