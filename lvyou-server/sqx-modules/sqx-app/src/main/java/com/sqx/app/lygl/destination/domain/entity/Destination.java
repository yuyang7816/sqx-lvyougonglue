package com.sqx.app.lygl.destination.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 目的地配置
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
@Getter
@Setter
@TableName("destination")
@Schema(name = "Destination", description = "目的地配置")
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "城市")
    @TableField("city")
    private String city;

    @Schema(description = "封面")
    @TableField("cover")
    private String cover;

    @Schema(description = "热门 1=是 2=否")
    @TableField("popular")
    private Integer popular;

    @Schema(description = "精选 1=是 2=否")
    @TableField("choiceness")
    private Integer choiceness;

    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "相册")
    private String album;

    @Schema(description = "境外 1=是 2=否")
    private Integer overseas;

}
