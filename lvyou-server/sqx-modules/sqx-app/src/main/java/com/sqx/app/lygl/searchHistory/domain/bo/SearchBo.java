package com.sqx.app.lygl.searchHistory.domain.bo;

import com.sqx.common.mybatis.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/17
 */
@Data
public class SearchBo extends PageParam {

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "搜索类型 1=动态 2=景点 3=路线 4=商家")
    private Integer type;

    private Long userId;
}
