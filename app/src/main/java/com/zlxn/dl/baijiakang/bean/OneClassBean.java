package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/17 13:48
 */
public class OneClassBean {

    /**
     * addAuditorsId : 5
     * addOperatorId : 5
     * addTime : 1605249884000
     * categoryName : 测试分类
     * id : 5
     * isUp : 0
     * level : 1
     * parentCategoryId : 0
     * pic : /img/haha.jpg
     * status : 1
     * updateAuditorsId : 5
     * updateOperatorId : 5
     * updateTime : 1605249884000
     */

    private int addAuditorsId;
    private int addOperatorId;
    private long addTime;
    private String categoryName;
    private int id;
    private int isUp;
    private int level;
    private int parentCategoryId;
    private String pic;
    private int status;
    private int updateAuditorsId;
    private int updateOperatorId;
    private long updateTime;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getAddAuditorsId() {
        return addAuditorsId;
    }

    public void setAddAuditorsId(int addAuditorsId) {
        this.addAuditorsId = addAuditorsId;
    }

    public int getAddOperatorId() {
        return addOperatorId;
    }

    public void setAddOperatorId(int addOperatorId) {
        this.addOperatorId = addOperatorId;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUpdateAuditorsId() {
        return updateAuditorsId;
    }

    public void setUpdateAuditorsId(int updateAuditorsId) {
        this.updateAuditorsId = updateAuditorsId;
    }

    public int getUpdateOperatorId() {
        return updateOperatorId;
    }

    public void setUpdateOperatorId(int updateOperatorId) {
        this.updateOperatorId = updateOperatorId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
