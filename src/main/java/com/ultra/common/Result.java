package com.ultra.common;

/**
 * @Date: 2018/10/17 0017 10:09
 * @Description:
 */
public class Result {
    /** 消息 */
    private String message;
    /** 数据 */
    private Object data;

    public Result() {
    }

    public Result(String message) {
        super();
        this.message = message;
    }

    public Result(String message, Object data) {
        super();
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result [message=" + message + ", data=" + data + "]";
    }

}
