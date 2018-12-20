package com.ultra.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ultra.common.Result;

public class ResponseEntityGenerator {
    private static final String DEFAULT_SUCCESS = "SUCCESS";
    private static final String DEFAULT_FAILURE = "FAILURE";

    /**
     * 
     * @Title ok
     * @Description 成功:返回默认值
     * @return ResponseEntity<Result>
     */
    public static ResponseEntity<Result> ok() {
        return new ResponseEntity<Result>(new Result(DEFAULT_SUCCESS), HttpStatus.OK);
    }

    /**
     * 
     * @Title ok
     * @Description 成功:自定义返回值
     * @return ResponseEntity<Result>
     */
    public static ResponseEntity<Result> ok(Object data) {
        return new ResponseEntity<Result>(new Result(DEFAULT_SUCCESS, data), HttpStatus.OK);
    }

    /**
     * 
     * @Title ok
     * @Description 400 Bad Request:返回默认值
     * @return ResponseEntity<String>
     */
    public static ResponseEntity<Result> bad() {
        return new ResponseEntity<Result>(new Result(DEFAULT_FAILURE), HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @Title bad
     * @Description 400 Bad Request:自定义返回值类型
     * @return ResponseEntity<T>
     */
    public static ResponseEntity<Result> bad(String message) {
        return new ResponseEntity<Result>(new Result(message), HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @Title bad
     * @Description 400 Bad Request:自定义返回值类型
     * @return ResponseEntity<T>
     */
    @Deprecated
    public static ResponseEntity<Result> bad(String message, Object data) {
        return new ResponseEntity<Result>(new Result(message, data), HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @Title error
     * @Description 500 Internal Server Error:返回值字符串
     * @return ResponseEntity<String>
     */
    public static ResponseEntity<Result> error() {
        return new ResponseEntity<Result>(new Result(DEFAULT_FAILURE), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 
     * @Title error
     * @Description 500 Internal Server Error:返回异常信息
     * @return ResponseEntity<T>
     */
    public static ResponseEntity<Result> error(String message) {
        return new ResponseEntity<Result>(new Result(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 
     * @Title error
     * @Description 500 Internal Server Error:自定义返回值类型
     * @return ResponseEntity<T>
     */
    @Deprecated
    public static ResponseEntity<Result> error(String message, Result data) {
        return new ResponseEntity<Result>(new Result(message, data), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 
     * @Title general
     * @Description 通用:自定义状态码,返回值
     * @return ResponseEntity<T>
     */
    public static ResponseEntity<Result> general(String message, Result data, HttpStatus status) {
        return new ResponseEntity<Result>(new Result(message, data), status);
    }
}
