package com.zlxn.dl.baijiakang.bean;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 11:07
 */
public class CustomViewsInfo implements BaseBannerInfo {

    private String info;

    public CustomViewsInfo(String info) {
        this.info = info;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }

    @Override
    public String getXBannerTitle() {
        return null;
    }

}
