package com.zlxn.dl.baijiakang.http;

import java.io.IOException;

/**
 * @author DL
 * @name BossApp
 * @class describe
 * @time 2020/5/13 10:50
 */
public class ResultException extends IOException {

    private String message;
    private int code;
    private String data;

    public ResultException(String message, int code,String data){
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return message;
    }

    public void setErrMsg(String message) {
        this.message = message;
    }

    public int getErrCode() {
        return code;
    }

    public void setErrCode(int code) {
        this.code = code;
    }

}
