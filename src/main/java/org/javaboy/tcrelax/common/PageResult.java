package org.javaboy.tcrelax.common;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class PageResult <T> {

    private T data;

    /**
     * 总大小
     */
    private Long totalSize;

    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 当前页返回数据
     */
    private Integer curPageSize;

}
