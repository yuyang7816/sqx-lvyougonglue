package com.sqx.app.lygl.collect.domain.vo;

import com.sqx.app.lygl.collect.entity.TravelCollect;
import com.sqx.app.lygl.moments.domain.vo.MomentsVo;
import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.router.baseInfo.entity.TravelBaseInfo;
import com.sqx.app.lygl.scenicSpot.info.entity.TravelScenicSpot;
import com.sqx.app.lygl.shop.shopInfo.entity.TravelShop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CollectVo extends TravelCollect {
    /**
     * 动态
     */
    @Schema(description = "动态")
    private MomentsVo moments;

    /**
     * 景点
     */
    @Schema(description = "景点")
    private TravelScenicSpot scenicSpot;

    /**
     * 路线
     */
    @Schema(description = "路线")
    private TravelBaseInfo baseInfo;

    /**
     * 商户
     */
    @Schema(description = "商户")
    private TravelShop shop;

    @Schema(description = "收藏数")
    private Long collectNum;
}
