package com.sqx.app.lygl.concert.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConcertPageBo extends PageParam {

    /**
     * 标题
     */
    @Schema(description = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 演出地点
     */
    @Schema(description = "演出地点")
    @TableField(value = "location")
    private String location;
}
