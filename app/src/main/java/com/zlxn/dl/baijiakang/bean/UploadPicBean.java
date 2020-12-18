package com.zlxn.dl.baijiakang.bean;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/17 10:24
 */
public class UploadPicBean {

    /**
     * imgUrl : /uploadFiles/img/2020-11-17/1605579831985.png
     * status : true
     */

    private String imgUrl;
    private boolean status;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
