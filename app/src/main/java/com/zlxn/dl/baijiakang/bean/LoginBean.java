package com.zlxn.dl.baijiakang.bean;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 10:33
 */
public class LoginBean {

    /**
     * userType : 2
     * user : {"id":4,"userName":"textdl","password":"$2a$10$Q5XP0J2wqdgD2mgJVPTYp.6AbG7FDcYGy0vVPh9RLL1Ael2ZcQRZa","registrationTime":1605060501000,"lastLoginTime":1605061958493,"openId":"123","userType":2,"wechatName":"测试"}
     * token : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXh0ZGwtW10iLCJleHAiOjE2MDY1MzMxODd9.WbMqYzy6qAbiuKWrl1RiIBb43Kv9G1aVf4XjoMy7vPXvT64qyvzsk1sTVtk-ythw950F5MzRI9PbdI6_3eZgdQ
     */

    private int userType;
    private UserBean user;
    private String token;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * id : 4
         * userName : textdl
         * password : $2a$10$Q5XP0J2wqdgD2mgJVPTYp.6AbG7FDcYGy0vVPh9RLL1Ael2ZcQRZa
         * registrationTime : 1605060501000
         * lastLoginTime : 1605061958493
         * openId : 123
         * userType : 2
         * wechatName : 测试
         */

        private int id;
        private String userName;
        private String password;
        private long registrationTime;
        private long lastLoginTime;
        private String openId;
        private int userType;
        private String payPassword;
        private String wechatName;
        private String pic;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }



        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPayPassword() {
            return payPassword;
        }

        public void setPayPassword(String payPassword) {
            this.payPassword = payPassword;
        }

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

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
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
    }
}
