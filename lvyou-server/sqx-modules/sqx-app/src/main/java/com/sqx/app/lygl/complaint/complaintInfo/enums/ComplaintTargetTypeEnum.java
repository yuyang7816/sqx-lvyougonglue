package com.sqx.app.lygl.complaint.complaintInfo.enums;

/**
 * @author chengming
 * @since 2024/10/14
 */
public enum ComplaintTargetTypeEnum {

    /**
     * 投诉目标类型 1用户 2群聊
     */
    USER(1, "用户"),
    GROUP(2, "群聊")
    ;

    private final Integer code;
    private final String desc;

    ComplaintTargetTypeEnum(Integer code, String desc) {
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
