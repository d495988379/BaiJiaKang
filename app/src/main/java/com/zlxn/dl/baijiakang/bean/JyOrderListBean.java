package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/15 17:33
 */
public class JyOrderListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10018
         * orderid : HY0000091712012151PO802
         * paysn : CZBH323271829714001
         * phone : 139****6520
         * orderdt : 2020-12-15 17:07:13
         * paydt : null
         * gasname : 航油加油一站（机场路站）
         * province : 天津市
         * city : 市辖区
         * county : 东丽区
         * gunno : 16
         * oilno : 92
         * amountpay : 5.05
         * amountgun : 5.54
         * orderstatusname : 已支付
         * ispaymoney : 1
         * parentid : fa8477f4df34a202008131935
         * usermobile : 13930546520
         * litre : 1.0
         * parentName : null
         */

        private int id;
        private String orderid;
        private String paysn;
        private String phone;
        private String orderdt;
        private Object paydt;
        private String gasname;
        private String province;
        private String city;
        private String county;
        private int gunno;
        private String oilno;
        private double amountpay;
        private double amountgun;
        private String orderstatusname;
        private int ispaymoney;
        private String parentid;
        private String usermobile;
        private String litre;
        private Object parentName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getPaysn() {
            return paysn;
        }

        public void setPaysn(String paysn) {
            this.paysn = paysn;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOrderdt() {
            return orderdt;
        }

        public void setOrderdt(String orderdt) {
            this.orderdt = orderdt;
        }

        public Object getPaydt() {
            return paydt;
        }

        public void setPaydt(Object paydt) {
            this.paydt = paydt;
        }

        public String getGasname() {
            return gasname;
        }

        public void setGasname(String gasname) {
            this.gasname = gasname;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public int getGunno() {
            return gunno;
        }

        public void setGunno(int gunno) {
            this.gunno = gunno;
        }

        public String getOilno() {
            return oilno;
        }

        public void setOilno(String oilno) {
            this.oilno = oilno;
        }

        public double getAmountpay() {
            return amountpay;
        }

        public void setAmountpay(double amountpay) {
            this.amountpay = amountpay;
        }

        public double getAmountgun() {
            return amountgun;
        }

        public void setAmountgun(double amountgun) {
            this.amountgun = amountgun;
        }

        public String getOrderstatusname() {
            return orderstatusname;
        }

        public void setOrderstatusname(String orderstatusname) {
            this.orderstatusname = orderstatusname;
        }

        public int getIspaymoney() {
            return ispaymoney;
        }

        public void setIspaymoney(int ispaymoney) {
            this.ispaymoney = ispaymoney;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getUsermobile() {
            return usermobile;
        }

        public void setUsermobile(String usermobile) {
            this.usermobile = usermobile;
        }

        public String getLitre() {
            return litre;
        }

        public void setLitre(String litre) {
            this.litre = litre;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }
    }
}
