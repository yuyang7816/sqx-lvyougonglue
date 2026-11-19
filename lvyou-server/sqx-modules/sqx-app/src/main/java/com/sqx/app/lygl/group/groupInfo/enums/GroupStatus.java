package com.sqx.app.lygl.group.groupInfo.enums;

import java.util.Objects;

/**
 * @author chengming
 * @since 2024/10/12
 */
public enum GroupStatus {
    /**
     * 群状态（0正常 1禁用 2过期 3人数已达最大）
     */
    ENABLE(0, "正常"),
    DISABLE(1, "禁用"),
    EXPIRE(2, "过期"),
    MAX_PEOPLE(3, "人数已达最大")
    ;

    private final Integer code;
    private final String desc;

    GroupStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    /**
     * 根据code获取desc
     * @param code code
     */
    public static String getDescByCode(Integer code) {
        GroupStatus[] values = GroupStatus.values();
        for (GroupStatus value : values) {
            if (Objects.equals(code, value.code())) {
                return value.desc();
            }
        }

       throw new IllegalArgumentException("群聊状态不存在");
    }
}
