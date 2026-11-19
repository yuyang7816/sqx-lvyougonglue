package com.sqx.app.lygl.moments.domain.vo;


import com.sqx.app.lygl.moments.entity.TravelMoments;
import com.sqx.app.lygl.scenicSpot.info.domain.vo.ScenicSpotVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MomentsVo extends TravelMoments {

    /**
     * 景点信息
     */
    @Schema(description = "景点信息")
    private ScenicSpotVo scenicSpotVo;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    private Long likeNum;

    /**
     * 收藏数
     */
    @Schema(description = "收藏数")
    private Long collectNum;

    /**
     * 评论数
     */
    @Schema(description = "评论数")
    private Long commentNum;

    @Schema(description = "发布人用户名")
    private String userName;

    @Schema(description = "发布人头像")
    private String avatar;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "性别 1男 2女")
    private Integer sex;

    @Schema(description = "是否点赞")
    private Boolean isLike;

    @Schema(description = "是否收藏")
    private Boolean isCollect;

    @Schema(description = "景点名称")
    private String scenicSpotName;

    @Schema(description = "景点位置")
    private String location;

    @Schema(description = "商铺名称")
    private String shopName;
}
