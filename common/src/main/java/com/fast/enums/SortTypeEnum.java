package com.fast.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 排序枚举
 * Created by daiyitian on 2017/4/22.
 */
public enum SortTypeEnum {
    /**
     * asc:升序
     */
    ASC("asc"),

    /**
     * desc:倒序
     */
    DESC("desc");

    private final String value;

    SortTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
