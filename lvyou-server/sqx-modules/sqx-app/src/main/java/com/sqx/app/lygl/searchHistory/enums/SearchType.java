package com.sqx.app.lygl.searchHistory.enums;

import java.util.Objects;

/**
 * @author chengming
 * @since 2025/10/9
 */
public enum SearchType {
    MOMENTS(1, "动态"),
    SCENIC_SPOT(2, "景点"),
    ROUTE(3, "路线"),
    SHOP(4, "商户")
    ;

    private final Integer type;
    private final String desc;

    SearchType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
