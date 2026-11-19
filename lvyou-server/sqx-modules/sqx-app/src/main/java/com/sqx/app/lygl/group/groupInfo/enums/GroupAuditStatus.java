package com.sqx.app.lygl.group.groupInfo.enums;

/**
 * @author chengming
 * @since 2024/10/12
 */
public enum GroupAuditStatus {
    /**
     * 审核状态（0待审核 1审核通过 2审核拒绝）
     */
    WAIT(0, "待审核"),
    PASS(1, "审核通过"),
    REFUSE(2, "审核拒绝")
    ;

    private final Integer code;
    private final String desc;

    GroupAuditStatus(Integer code, String desc) {
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
