package com.sqx.app.lygl.searchHistory.domain.vo;

import com.sqx.app.lygl.searchHistory.domain.entity.SearchHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-09-17 13:23:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchHistoryVo extends SearchHistory {

    @Schema(description = "出现次数")
    private Long count;
}
