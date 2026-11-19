package com.sqx.app.lygl.concert.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/10/10
 */
@Data
public class ConcertSaveBo {

    /**
     * 封面图
     */
    @Schema(description = "封面图片的URL")
    private String img;

    /**
     * 详情图
     */
    @Schema(description = "详情图")
    private String pic;

    /**
     * 标题
     */
    @Schema(description = "演出标题")
    private String title;

    /**
     * 演出时间
     */
    @Schema(description = "演出的具体时间", type = "string", format = "date-time", example = "2025-06-10 13:04:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime concertTime;

    /**
     * 演出地点
     */
    @Schema(description = "演出发生的地点")
    private String location;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;
}
