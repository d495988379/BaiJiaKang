package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 11:44
 */
public class CommodityListBean {

    /**
     * total : 14
     * rows : [{"id":3,"productNumber":"123","productName":"测试商品3","productCategoryId":21,"specification":null,"price":49.9,"unit":null,"productContent":null,"status":2,"addTime":1604892268000,"addOperatorId":null,"updateTime":null,"updateOperatorId":null,"shelfTime":null,"shelfOperatorId":null,"isChose":1,"pic":"/uploadFiles/2020-11-11/timg.jpg","originalPrice":0,"categoryName":null}]
     * allPage : 14
     */

    private int allPage;
    private List<RowsBean> rows;


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
         * id : 3
         * productNumber : 123
         * productName : 测试商品3
         * productCategoryId : 21
         * specification : null
         * price : 49.9
         * unit : null
         * productContent : null
         * status : 2
         * addTime : 1604892268000
         * addOperatorId : null
         * updateTime : null
         * updateOperatorId : null
         * shelfTime : null
         * shelfOperatorId : null
         * isChose : 1
         * pic : /uploadFiles/2020-11-11/timg.jpg
         * originalPrice : 0
         * categoryName : null
         */

        private int id;
        private String productNumber;
        private String productName;
        private int productCategoryId;
        private Object specification;
        private String price;
        private Object unit;
        private String productContent;
        private int status;
        private long addTime;
        private Object addOperatorId;
        private Object updateTime;
        private Object updateOperatorId;
        private Object shelfTime;
        private Object shelfOperatorId;
        private int isChose;
        private String pic;
        private String originalPrice;
        private Object categoryName;
        private int cardRoll;

        public int getCardRoll() {
            return cardRoll;
        }

        public void setCardRoll(int cardRoll) {
            this.cardRoll = cardRoll;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(int productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public Object getSpecification() {
            return specification;
        }

        public void setSpecification(Object specification) {
            this.specification = specification;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public String getProductContent() {
            return productContent;
        }

        public void setProductContent(String productContent) {
            this.productContent = productContent;
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

        public Object getAddOperatorId() {
            return addOperatorId;
        }

        public void setAddOperatorId(Object addOperatorId) {
            this.addOperatorId = addOperatorId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getUpdateOperatorId() {
            return updateOperatorId;
        }

        public void setUpdateOperatorId(Object updateOperatorId) {
            this.updateOperatorId = updateOperatorId;
        }

        public Object getShelfTime() {
            return shelfTime;
        }

        public void setShelfTime(Object shelfTime) {
            this.shelfTime = shelfTime;
        }

        public Object getShelfOperatorId() {
            return shelfOperatorId;
        }

        public void setShelfOperatorId(Object shelfOperatorId) {
            this.shelfOperatorId = shelfOperatorId;
        }

        public int getIsChose() {
            return isChose;
        }

        public void setIsChose(int isChose) {
            this.isChose = isChose;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }
    }
}
