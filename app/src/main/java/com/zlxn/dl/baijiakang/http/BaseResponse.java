package com.zlxn.dl.baijiakang.http;

/**
 * @author DL
 * @name RetrofitTest
 * @class describe
 * @time 2019/10/30 17:26
 */

/**
 * 统一响应（修改）
 *
 * @param <T>
 */
public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
