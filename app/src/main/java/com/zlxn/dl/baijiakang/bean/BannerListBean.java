package com.zlxn.dl.baijiakang.bean;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 10:54
 */
public class BannerListBean  {

    /**
     * id : 1
     * pic : /uploadFiles/img/2020-11-11/1605057934131.jpg
     * version : 1
     */

    private int id;
    private String pic;
    private int version;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
