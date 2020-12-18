package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/4 14:37
 */
public class BalanceRecordsBean {
    /**
     * total : 1
     * data : [{"id":20,"userId":0,"payMoney":140,"createTime":1607061192000,"type":"用户余额消费","status":1,"byUserId":1,"salesId":1607061191677,"byUserName":"测试账号1"}]
     * allPage : 1
     */

    private int total;
    private int allPage;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAllPage() {
        return allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 20
         * userId : 0
         * payMoney : 140
         * createTime : 1607061192000
         * type : 用户余额消费
         * status : 1
         * byUserId : 1
         * salesId : 1607061191677
         * byUserName : 测试账号1
         */

        private int id;
        private int userId;
        private String payMoney;
        private long createTime;
        private String type;
        private int status;
        private int byUserId;
        private long salesId;
        private String byUserName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getByUserId() {
            return byUserId;
        }

        public void setByUserId(int byUserId) {
            this.byUserId = byUserId;
        }

        public long getSalesId() {
            return salesId;
        }

        public void setSalesId(long salesId) {
            this.salesId = salesId;
        }

        public String getByUserName() {
            return byUserName;
        }

        public void setByUserName(String byUserName) {
            this.byUserName = byUserName;
        }
    }
}
