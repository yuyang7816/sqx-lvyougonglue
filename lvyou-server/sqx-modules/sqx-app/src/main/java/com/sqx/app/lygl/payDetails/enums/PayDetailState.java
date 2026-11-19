package com.sqx.app.lygl.payDetails.enums;

/**
 * @author chengming
 * @since 2024/9/18
 */
public enum PayDetailState {
    /**
     * 支付详情状态 0待支付 1支付成功 2失败
     */
    WAIT_PAY(0, "待支付"),
    SUCCESS(1, "支付成功"),
    FAIL(2, "失败")
    ;

    private final Integer code;
    private final String desc;

    PayDetailState(Integer code, String desc) {
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
