package com.fast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询返回基类
 * */
@Data
public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = -352594439173761171L;

    /**
     * 页码
     */
    private int number;

    /**
     * 每页条数
     */
    private int size;

    /**
     * 总数据大小
     */
    private long total;

    /**
     * 分页对象
     */
    @JsonIgnore
    private Pageable pageable;

    /**
     * 具体数据内容
     */
    private List<T> content = new ArrayList<>();

}
