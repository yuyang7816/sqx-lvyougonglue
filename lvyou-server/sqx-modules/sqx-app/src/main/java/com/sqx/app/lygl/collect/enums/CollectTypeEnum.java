package com.sqx.app.lygl.collect.enums;

/**
 * @author chengming
 * @since 2024/10/11
 */
public enum CollectTypeEnum {
    /**
     * 1动态 2景点 3路线
     */
    MOMENTS(1, "动态"),
    SCENIC_SPOT(2, "景点"),
    ROUTE(3, "路线"),
    SHOP(4, "商户")
    ;

    private final Integer code;
    private final String desc;

    CollectTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
