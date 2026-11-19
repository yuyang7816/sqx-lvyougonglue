package com.sqx.app.lygl.moments.enums;

/**
 * @author chengming
 * @since 2025/8/18
 */
public enum AuditStatus {
    /**
     * 审核状态：0-待审核, 1-审核通过, 2-审核拒绝
     */
    PENDING(0, "待审核"),
    APPROVED(1, "审核通过"),
    REJECTED(2, "审核拒绝");
    ;

    private final Integer code;
    private final String desc;

    AuditStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
