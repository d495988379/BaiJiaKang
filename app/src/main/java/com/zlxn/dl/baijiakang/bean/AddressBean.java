package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 17:09
 */
public class AddressBean {

    private List<AddressListBean> addressList;

    public List<AddressListBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressListBean> addressList) {
        this.addressList = addressList;
    }

    public static class AddressListBean {
        /**
         * id : 3
         * phone : 15522534471
         * userId : 4
         * userName : 测试段
         * area : 天津
         * detailArea : 测试测试
         */

        private int id;
        private String phone;
        private int userId;
        private String userName;
        private String area;
        private String detailArea;
        private int confirm;

        public int getConfirm() {
            return confirm;
        }

        public void setConfirm(int confirm) {
            this.confirm = confirm;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetailArea() {
            return detailArea;
        }

        public void setDetailArea(String detailArea) {
            this.detailArea = detailArea;
        }
    }
}
