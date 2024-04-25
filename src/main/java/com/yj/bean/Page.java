package com.yj.bean;

import java.util.List;

/**
 * @author 阳健
 * @<T>具体要分页的对象类型
 * 概要：
 *     分页模型
 */

public class Page<T> {

    public static final Integer PAGE_SIZE = 4;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 总页码
     */
    private Integer pageTotal;

    /**
     * 总记录数
     */
    private Integer recordTotal;

    /**
     * 每页容纳数量
     */
    private Integer pageSize = PAGE_SIZE;

    /**
     * 当前页数据
     */
    private List<T> items;

    /**分页条的url地址
     *
     */
    private String url;

    /**
     * 无参数构造器
     */
    public Page() {
    }

    /**
     * 全参数的构造器
     * @param pageNo       当前页
     * @param pageTotal    总页数
     * @param recordTotal  记录总数
     * @param pageSize     单页容量
     * @param items        当前页记录集合
     * @param url          URL地址
     */
    public Page(Integer pageNo, Integer pageTotal, Integer recordTotal, Integer pageSize, List<T> items, String url) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.recordTotal = recordTotal;
        this.pageSize = pageSize;
        this.items = items;
        this.url = url;
    }

    /**
     * 重写toString方法
     * @return  分页对象
     */
    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", recordTotal=" + recordTotal +
                ", pageSize=" + pageSize +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
        this.recordTotal = recordTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
