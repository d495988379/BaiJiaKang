package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/17 16:00
 */
public class ChildBean {

    /**
     * id : 17
     * categoryName : 健康食品
     * parentCategoryId : 9
     * status : 1
     * addTime : 1605582386000
     * addOperatorId : 9
     * addAuditorsId : 9
     * updateTime : 1605582386000
     * updateOperatorId : 9
     * updateAuditorsId : 9
     * pic : http://39.99.202.81:8090/uploadFiles/img/2020-11-17/1605582366209.png
     * level : 2
     * isUp : 0
     */

    private int id;
    private String categoryName;
    private int parentCategoryId;
    private int status;
    private long addTime;
    private int addOperatorId;
    private int addAuditorsId;
    private long updateTime;
    private int updateOperatorId;
    private int updateAuditorsId;
    private String pic;
    private int level;
    private int isUp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public int getAddOperatorId() {
        return addOperatorId;
    }

    public void setAddOperatorId(int addOperatorId) {
        this.addOperatorId = addOperatorId;
    }

    public int getAddAuditorsId() {
        return addAuditorsId;
    }

    public void setAddAuditorsId(int addAuditorsId) {
        this.addAuditorsId = addAuditorsId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateOperatorId() {
        return updateOperatorId;
    }

    public void setUpdateOperatorId(int updateOperatorId) {
        this.updateOperatorId = updateOperatorId;
    }

    public int getUpdateAuditorsId() {
        return updateAuditorsId;
    }

    public void setUpdateAuditorsId(int updateAuditorsId) {
        this.updateAuditorsId = updateAuditorsId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }
}
