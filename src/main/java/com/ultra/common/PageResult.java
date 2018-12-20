package com.ultra.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description 改造成前台需要的属性名
 * @date 2018年12月20日 下午3:26:57
 * @param <T>
 */
public class PageResult<T> {

    private int pageNo;
    private int size;
    private int pages;
    private int total;
    private List<T> rows = new ArrayList<T>();

    public PageResult() {
    }

    public PageResult(int pageNo, int size, int pages, int total, List<T> rows) {
        super();
        this.pageNo = pageNo;
        this.size = size;
        this.pages = pages;
        this.total = total;
        this.rows = rows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult [pageNo=" + pageNo + ", size=" + size + ", pages=" + pages + ", total=" + total + ", rows="
                + rows + "]";
    }

}
