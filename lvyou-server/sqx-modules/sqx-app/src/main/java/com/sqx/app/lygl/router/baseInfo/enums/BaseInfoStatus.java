package com.sqx.app.lygl.router.baseInfo.enums;

/**
 * @author chengming
 * @since 2024/10/10
 */
public enum BaseInfoStatus {
    /**
     * 路线基本信息 状态 1正常 2禁用
     */
    ENABLE(1, "正常"),
    DISABLE(2, "禁用")
    ;

    private final Integer status;
    private final String desc;

    BaseInfoStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
