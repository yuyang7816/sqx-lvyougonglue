package com.sqx.app.lygl.group.groupUser.enums;

/**
 * @author chengming
 * @since 2024/10/12
 */
public enum GroupUserStatus {
    /**
     * 用户状态 1正常 2已到期/禁用
     */
    ENABLE(1, "正常"),
    DISABLE(2, "已到期/禁用")
    ;

    private final Integer code;
    private final String desc;

    GroupUserStatus(Integer code, String desc) {
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
