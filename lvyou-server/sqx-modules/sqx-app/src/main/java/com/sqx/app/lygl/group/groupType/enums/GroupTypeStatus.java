package com.sqx.app.lygl.group.groupType.enums;

/**
 * @author chengming
 * @since 2024/10/12
 */
public enum GroupTypeStatus {
    /**
     * 群聊类型状态（0正常 1禁用）
     */
    ENABLE(0, "正常"),
    DISABLE(1, "禁用")
    ;

    private final Integer code;
    private final String desc;

    GroupTypeStatus(Integer code, String desc) {
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
