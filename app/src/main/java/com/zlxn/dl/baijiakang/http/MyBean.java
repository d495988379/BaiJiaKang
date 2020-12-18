package com.zlxn.dl.baijiakang.http;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/3 17:31
 */
public class MyBean {


    /**
     * code : 400
     * message : 暂无数据
     * data : 暂无数据
     */

    private int code;
    private String message;
    private String data;


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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
