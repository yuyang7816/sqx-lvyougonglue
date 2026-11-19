package com.sqx.app.lygl.complaint.complaintInfo.enums;

/**
 * @author chengming
 * @since 2024/10/14
 */
public enum ComplaintStatus {
    /**
     * 投诉状态 1等待处理 2封禁账号 3已驳回 4通过但不做处理
     */
    WAIT(1, "等待处理"),
    BLOCK(2, "封禁"),
    REFUSED(3, "已驳回"),
    PASS(4, "通过但不做处理")
    ;

    private final Integer code;
    private final String desc;

    ComplaintStatus(Integer code, String desc) {
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
