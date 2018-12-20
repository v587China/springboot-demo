package com.ultra.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.plugins.Page;
import com.ultra.common.PageResult;

public class PageUtil {
    private final static Logger logger = LoggerFactory.getLogger(PageUtil.class);

    private PageUtil() {
    }

    /**
     * 
     * @Title createPage
     * @Description 构建查询条件的Page对象(当前页,每页条数,排序字段,升降序)
     * @return Page<T>
     */
    public static <T> Page<T> createPage(HttpServletRequest request) {
        Page<T> page = new Page<T>();
        String noStr = request.getParameter("pageNo");
        String sizeStr = request.getParameter("size");
        String order = request.getParameter("order");
        String isAscStr = request.getParameter("isAsc");
        int pageNo = 1;
        int size = 10;
        boolean isAsc = true;
        if (StringUtils.isNotBlank(noStr)) {
            try {
                pageNo = Integer.parseInt(noStr);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (StringUtils.isNotBlank(sizeStr)) {
            try {
                size = Integer.parseInt(sizeStr);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (StringUtils.isNotBlank(order)) {
            page.setOrderByField(order);
            if (StringUtils.isNotBlank(isAscStr)) {
                try {
                    isAsc = isAscStr.equals("true");
                } catch (Exception e) {
                    logger.error("", e);
                }
                page.setAsc(isAsc);
            }
        } else {
            page.setOrderByField("id");
            page.setAsc(false);
        }
        page.setCurrent(pageNo);
        page.setSize(size);
        return page;
    }

    /**
     * 
     * @Title createPageResult
     * @Description 构建分页查询结果对象,用于转json返回前台
     * @return PageResult<T>
     */
    public static <T> PageResult<T> createPageResult(Page<T> page) {
        return new PageResult<T>(page.getCurrent(), page.getSize(), page.getPages(), page.getTotal(),
                page.getRecords());
    }
}
