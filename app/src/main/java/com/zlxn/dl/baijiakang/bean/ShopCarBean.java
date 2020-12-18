package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/12 11:40
 */
public class ShopCarBean {
    /**
     * code : 200
     * message : success
     * data : {"data":[{"id":3,"productId":1,"userId":4,"number":1,"price":84,"createTime":1605075561000,"ticket":0,"pic":"/uploadFiles/2020-11-11/timg.jpg","productName":"测试商品"},{"id":4,"productId":1,"userId":4,"number":1,"price":23,"createTime":1605075561000,"ticket":0,"pic":"/uploadFiles/2020-11-11/timg.jpg","productName":"测试商品"},{"id":5,"productId":3,"userId":4,"number":1,"price":50,"createTime":1605147644000,"ticket":0,"pic":"/uploadFiles/2020-11-11/timg.jpg","productName":"测试商品3"}]}
     */
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * productId : 1
         * userId : 4
         * number : 1
         * price : 84
         * createTime : 1605075561000
         * ticket : 0
         * pic : /uploadFiles/2020-11-11/timg.jpg
         * productName : 测试商品
         */

        private int id;
        private int productId;
        private int userId;
        private int number;
        private String price;
        private long createTime;
        private int ticket;
        private String pic;
        private String specification;
        private String stageId;
        private String productName;
        private boolean isSelect;

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getTicket() {
            return ticket;
        }

        public void setTicket(int ticket) {
            this.ticket = ticket;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
