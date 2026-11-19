package com.sqx.app.lygl.group.groupInfo.enums;

/**
 * @author chengming
 * @since 2024/10/12
 */
public enum GroupApplyCondition {
    /**
     * 申请类型（0免费，1付费）
     */
    FREE(0, "免费"),
    PAID(1, "付费"),
    ;

    private final Integer code;
    private final String desc;

    GroupApplyCondition(Integer code, String desc) {
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
