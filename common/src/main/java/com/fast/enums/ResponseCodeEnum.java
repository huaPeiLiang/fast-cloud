package com.fast.enums;

public enum ResponseCodeEnum {


    成功(200,"SUCCESS"),
    网络异常(10001,"网络异常，请稍后再试！"),
    Token过期或已失效(10002, "The token has expired or become invalid"),
    第三方请求超时(10003, "第三方服务超时，请稍后再试！"),
    无法找到实例(10004, "Unable to find instance"),
    ;

    /**
     * code
     */
    public final int code;

    /**
     * message
     */
    public final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCodeEnum IsFastError(int code){
        for (ResponseCodeEnum e : ResponseCodeEnum.values()) {
            if (e.code == (code)) {
                return e;
            }
        }
        return null;
    }
//
//    public static String getMessage(String code){
//        for (ResponseCodeEnum e : ResponseCodeEnum.values()) {
//            if (e.code.equalsIgnoreCase(code)) {
//                return e.message;
//            }
//        }
//        return ResponseCodeEnum.网络异常.message;
//    }
}
