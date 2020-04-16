package com.fast.model;

import com.fast.enums.SortTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 请求基类
 */
@Data
public class BasePageRequest  implements Serializable {

    /**
     * 第几页
     */
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    private Integer pageSize = 10;

    /**
     * 排序类型
     */
    private String sortType;

    /**
     * 多重排序
     * 内容：key:字段,value:desc或asc
     */
    private Map<String, String> sortMap = new LinkedHashMap<>();

    /**
     * 获取分页参数对象与排序条件
     *
     * @return
     */
    public PageRequest getPageRequest() {
        //无排序
        Sort sort = getSort();
        if (Objects.nonNull(sort)) {
            return PageRequest.of(pageNum, pageSize, sort);
        } else {
            return PageRequest.of(pageNum, pageSize, Sort.unsorted());
        }
    }

    public Sort getSort() {
        if (sortMap == null || sortMap.isEmpty()) {
            List<Sort.Order> orders =
                    sortMap.keySet().stream().filter(StringUtils::isNotBlank)
                            .map(column -> new Sort.Order(SortTypeEnum.ASC.toValue().equalsIgnoreCase(sortMap.get(column)
                            ) ? Sort.Direction.ASC : Sort.Direction.DESC, column))
                            .collect(Collectors.toList());
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    /**
     * 获取分页参数对象
     *
     * @return
     */
    public PageRequest getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }

    /**
     * 填序排序
     *
     * @param column
     * @param sort
     */
    public void putSort(String column, String sort) {
        sortMap.put(column, sort);
    }
}
