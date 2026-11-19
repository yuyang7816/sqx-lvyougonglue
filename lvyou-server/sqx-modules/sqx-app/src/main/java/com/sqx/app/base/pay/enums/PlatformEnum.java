package com.sqx.app.base.pay.enums;

/**
 * @author chengming
 * @since 2025/4/22
 */
public enum PlatformEnum {
    ALI("2", "支付宝"),
    WX("1", "微信"),
//    BALANCE("5", "余额"),
    ;

    private final String code;
    private final String desc;

    PlatformEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
