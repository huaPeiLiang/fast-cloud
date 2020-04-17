package com.fast.enums;

public enum ErrorEnum {
    网络异常(10001,"网络异常，请稍后再试！"),
    ;

    /**
     * code
     */
    public final Integer code;

    /**
     * message
     */
    public final String message;

    private ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Integer messageOf(String message){
        for (ErrorEnum e : ErrorEnum.values()) {
            if (e.message.equalsIgnoreCase(message)) {
                return e.code;
            }
        }
        return -1;
    }
}
