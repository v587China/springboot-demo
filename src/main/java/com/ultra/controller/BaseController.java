package com.ultra.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ultra.common.PageResult;
import com.ultra.common.Result;
import com.ultra.util.CommonUtil;
import com.ultra.util.PageUtil;
import com.ultra.util.ResponseEntityGenerator;

@Component
public abstract class BaseController<M extends IService<T>, T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected M baseService;
    @Autowired
    protected MessageSource messageSource;

    /**
     * 
     * @Title select
     * @Description 通用分页搜索查询
     * @return ResponseEntity<PageResult<T>>
     */
    @SuppressWarnings("unchecked")
    protected ResponseEntity<Result> select(HttpServletRequest request, String fieldStr) {
        Page<T> page = null;
        String search = null;
        try {
            page = PageUtil.createPage(request);
            search = request.getParameter("search");
            Wrapper<T> wrapper = null;
            if (StringUtils.isNotBlank(search) && StringUtils.isNotBlank(fieldStr)) {
                wrapper = new Condition();
                String[] fields = fieldStr.split(",");
                for (int i = 0; i < fields.length; i++) {
                    wrapper = wrapper.like(fields[i], search);
                    if (i != fields.length - 1) {
                        wrapper = wrapper.or();
                    }
                }
            }
            page = baseService.selectPage(page, wrapper);
            PageResult<T> pageResult = PageUtil.createPageResult(page);
            return ResponseEntityGenerator.ok(pageResult);
        } catch (Exception e) {
            logger.error("page:" + page + ",search:" + search, e);
            return ResponseEntityGenerator.error(e.toString());
        }
    }

    /**
     * 
     * @Title select
     * @Description 通用查询所有
     * @return ResponseEntity<List<T>>
     */
    protected ResponseEntity<Result> select(Wrapper<T> wrapper) {
        try {
            List<T> list = baseService.selectList(wrapper);
            return ResponseEntityGenerator.ok(list);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseEntityGenerator.bad(e.toString());
        }
    }

    /**
     * 
     * @Title insert
     * @Description 通用插入
     * @return ResponseEntity<Result>
     */
    protected ResponseEntity<Result> insert(T t, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return CommonUtil.fieldIllegalMess(bindingResult, messageSource);
            }
            boolean insert = baseService.insert(t);
            if (insert) {
                return ResponseEntityGenerator.ok();
            }
            logger.error("param:" + t + ",unknown error.");
            return ResponseEntityGenerator.bad("unknown error.");
        } catch (Exception e) {
            logger.error("param:{},error:{}.", t, e);
            return ResponseEntityGenerator.error(e.toString());
        }
    }

    /**
     * 
     * @Title update
     * @Description 通用修改
     * @return ResponseEntity<Result>
     */
    protected ResponseEntity<Result> update(T t, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return CommonUtil.fieldIllegalMess(bindingResult, messageSource);
            }
            boolean update = baseService.updateById(t);
            if (update) {
                return ResponseEntityGenerator.ok();
            }
            logger.error("param:" + t + ",unknown error.");
            return ResponseEntityGenerator.bad("unknown error.");
        } catch (Exception e) {
            logger.error("param:{},error:{}.", t, e);
            return ResponseEntityGenerator.error(e.toString());
        }
    }

    /**
     * 
     * @Title delete
     * @Description 通用批量删除
     * @return ResponseEntity<Result>
     */
    protected ResponseEntity<Result> delete(@RequestParam List<Serializable> ids) {
        try {
            if (CollectionUtils.isEmpty(ids)) {
                logger.error("ids is required.");
                return ResponseEntityGenerator.bad("ids is required.");
            }
            boolean deleteBatchIds = baseService.deleteBatchIds(ids);
            if (deleteBatchIds) {
                return ResponseEntityGenerator.ok();
            }
            logger.error("ids:" + ids + ",unknown error.");
            return ResponseEntityGenerator.bad("unknown error.");
        } catch (Exception e) {
            logger.error("ids:{},error:{}.", ids, e);
            return ResponseEntityGenerator.error(e.toString());
        }
    }
}
