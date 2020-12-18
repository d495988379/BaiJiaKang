package com.zlxn.dl.baijiakang.http;

/**
 * @author DL
 * @name RetrofitTest
 * @class describe
 * @time 2019/10/30 17:26
 */

/**
 * 图片统一响应（修改）
 *
 *
 */
public class BaseImgResponse {

    private String code;
    private String dataxz;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataxz() {
        return dataxz;
    }

    public void setDataxz(String dataxz) {
        this.dataxz = dataxz;
    }


}
