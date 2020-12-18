package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 15:37
 */
public class OrderDetailsBean {

    /**
     * orderDetail : [{"id":58,"salesId":54,"productId":3,"number":1,"price":49.9,"ticket":0,"pic":"/uploadFiles/2020-11-11/timg.jpg","productName":"测试商品3"}]
     * order : {"id":54,"productId":null,"number":null,"price":49.9,"salesUnitId":null,"salesUnitType":null,"userId":4,"orderTime":1605512221000,"paymentTime":null,"shippingType":null,"logisticNumber":null,"logisticsCompanyId":null,"shippingTime":null,"shippingOperatorId":null,"receivingTime":null,"status":1,"paymentType":null,"addressId":12,"description":null,"phone":"15522534473","userName":"测试磊","area":"天津","detailArea":"测试天津"}
     */

    private OrderBean order;
    private List<OrderDetailBean> orderDetail;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderBean {
        /**
         * id : 54
         * productId : null
         * number : null
         * price : 49.9
         * salesUnitId : null
         * salesUnitType : null
         * userId : 4
         * orderTime : 1605512221000
         * paymentTime : null
         * shippingType : null
         * logisticNumber : null
         * logisticsCompanyId : null
         * shippingTime : null
         * shippingOperatorId : null
         * receivingTime : null
         * status : 1
         * paymentType : null
         * addressId : 12
         * description : null
         * phone : 15522534473
         * userName : 测试磊
         * area : 天津
         * detailArea : 测试天津
         */

        private long id;
        private Object productId;
        private Object number;
        private String price;
        private Object salesUnitId;
        private Object salesUnitType;
        private int userId;
        private long orderTime;
        private Object paymentTime;
        private Object shippingType;
        private Object logisticNumber;
        private Object logisticsCompanyId;
        private Object shippingTime;
        private Object shippingOperatorId;
        private Object receivingTime;
        private int status;
        private int paymentType;
        private int addressId;
        private Object description;
        private String phone;
        private String userName;
        private String area;
        private String detailArea;
        private String ticket;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getSalesUnitId() {
            return salesUnitId;
        }

        public void setSalesUnitId(Object salesUnitId) {
            this.salesUnitId = salesUnitId;
        }

        public Object getSalesUnitType() {
            return salesUnitType;
        }

        public void setSalesUnitType(Object salesUnitType) {
            this.salesUnitType = salesUnitType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public Object getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(Object paymentTime) {
            this.paymentTime = paymentTime;
        }

        public Object getShippingType() {
            return shippingType;
        }

        public void setShippingType(Object shippingType) {
            this.shippingType = shippingType;
        }

        public Object getLogisticNumber() {
            return logisticNumber;
        }

        public void setLogisticNumber(Object logisticNumber) {
            this.logisticNumber = logisticNumber;
        }

        public Object getLogisticsCompanyId() {
            return logisticsCompanyId;
        }

        public void setLogisticsCompanyId(Object logisticsCompanyId) {
            this.logisticsCompanyId = logisticsCompanyId;
        }

        public Object getShippingTime() {
            return shippingTime;
        }

        public void setShippingTime(Object shippingTime) {
            this.shippingTime = shippingTime;
        }

        public Object getShippingOperatorId() {
            return shippingOperatorId;
        }

        public void setShippingOperatorId(Object shippingOperatorId) {
            this.shippingOperatorId = shippingOperatorId;
        }

        public Object getReceivingTime() {
            return receivingTime;
        }

        public void setReceivingTime(Object receivingTime) {
            this.receivingTime = receivingTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(int paymentType) {
            this.paymentType = paymentType;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

    public static class OrderDetailBean {
        /**
         * id : 58
         * salesId : 54
         * productId : 3
         * number : 1
         * price : 49.9
         * ticket : 0
         * pic : /uploadFiles/2020-11-11/timg.jpg
         * productName : 测试商品3
         */

        private int id;
        private long salesId;
        private int productId;
        private int number;
        private String price;
        private String ticket;
        private String pic;
        private String productName;
        private String specificationName;

        public String getSpecificationName() {
            return specificationName;
        }

        public void setSpecificationName(String specificationName) {
            this.specificationName = specificationName;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getSalesId() {
            return salesId;
        }

        public void setSalesId(long salesId) {
            this.salesId = salesId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
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

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
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
