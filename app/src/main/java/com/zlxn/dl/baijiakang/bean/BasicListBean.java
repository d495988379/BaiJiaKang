package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/27 20:57
 */
public class BasicListBean {

    /**
     * total : 1
     * rows : [{"id":1,"stageId":71,"payMoney":123,"createTime":1606481157000,"type":"用户购买获取分润","status":2,"byUserId":1,"byUserName":"qzx","salesId":1606481106154}]
     * allPage : 1
     */

    private int total;
    private int allPage;
    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 1
         * stageId : 71
         * payMoney : 123
         * createTime : 1606481157000
         * type : 用户购买获取分润
         * status : 2
         * byUserId : 1
         * byUserName : qzx
         * salesId : 1606481106154
         */

        private int id;
        private int stageId;
        private String payMoney;
        private long createTime;
        private String type;
        private int status;
        private int byUserId;
        private String byUserName;
        private long salesId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
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

        public String getByUserName() {
            return byUserName;
        }

        public void setByUserName(String byUserName) {
            this.byUserName = byUserName;
        }

        public long getSalesId() {
            return salesId;
        }

        public void setSalesId(long salesId) {
            this.salesId = salesId;
        }
    }
}
