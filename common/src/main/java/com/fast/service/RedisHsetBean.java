package com.fast.service;

import lombok.Data;

/**
 * 适用于redis hset 结构的对象
 * Created by daiyitian on 2016/12/24.
 */
@Data
public class RedisHsetBean {

    private String key;

    private String field;

    private String value;

}
