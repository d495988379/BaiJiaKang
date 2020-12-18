package com.zlxn.dl.baijiakang.bean;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/27 20:25
 */
public class UserListBean {

    /**
     * id : 2
     * userName : qzx2
     * userNumber : null
     * password : $2a$10$Q5XP0J2wqdgD2mgJVPTYp.6AbG7FDcYGy0vVPh9RLL1Ael2ZcQRZa
     * registrationTime : 1606361405000
     * lastLoginTime : 1606361407000
     * openId : null
     * userType : 2
     * wechatName :
     * pic : null
     * payPassword : null
     * phone : 12345678901
     * status : 1
     * roleId : null
     * userLevelDescriptionId : 0
     */

    private int id;
    private String userName;
    private Object userNumber;
    private String password;
    private long registrationTime;
    private long lastLoginTime;
    private Object openId;
    private int userType;
    private String wechatName;
    private Object pic;
    private Object payPassword;
    private String phone;
    private int status;
    private Object roleId;
    private int userLevelDescriptionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Object userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Object getOpenId() {
        return openId;
    }

    public void setOpenId(Object openId) {
        this.openId = openId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public Object getPic() {
        return pic;
    }

    public void setPic(Object pic) {
        this.pic = pic;
    }

    public Object getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(Object payPassword) {
        this.payPassword = payPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getRoleId() {
        return roleId;
    }

    public void setRoleId(Object roleId) {
        this.roleId = roleId;
    }

    public int getUserLevelDescriptionId() {
        return userLevelDescriptionId;
    }

    public void setUserLevelDescriptionId(int userLevelDescriptionId) {
        this.userLevelDescriptionId = userLevelDescriptionId;
    }
}
