package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 11:04
 */
public class OrderListBean {

    /**
     * allPage : 1
     * data : [{"order":{"addressId":35,"id":1606445237924,"orderTime":1606445238000,"price":35.6,"status":1,"ticket":73},"orderDetail":[{"id":163,"number":1,"pic":"/uploadFiles/img/2020-11-25/1606298563347.jpg","price":11,"productId":61,"productName":"雪碧","salesId":1606445237924,"specificationId":320,"specificationName":"2323","ticket":23},{"id":164,"number":1,"pic":"/uploadFiles/img/2020-11-21/1605971638367.jpg","price":24.6,"productId":53,"productName":"乐事薯片大礼包20201121","salesId":1606445237924,"specificationId":326,"specificationName":"【70g*4袋】美国经典原味*4袋","ticket":50}]}]
     * total : 1
     */

    private int allPage;
    private int total;
    private List<DataBean> data;

    public int getAllPage() {
        return allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order : {"addressId":35,"id":1606445237924,"orderTime":1606445238000,"price":35.6,"status":1,"ticket":73}
         * orderDetail : [{"id":163,"number":1,"pic":"/uploadFiles/img/2020-11-25/1606298563347.jpg","price":11,"productId":61,"productName":"雪碧","salesId":1606445237924,"specificationId":320,"specificationName":"2323","ticket":23},{"id":164,"number":1,"pic":"/uploadFiles/img/2020-11-21/1605971638367.jpg","price":24.6,"productId":53,"productName":"乐事薯片大礼包20201121","salesId":1606445237924,"specificationId":326,"specificationName":"【70g*4袋】美国经典原味*4袋","ticket":50}]
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
             * addressId : 35
             * id : 1606445237924
             * orderTime : 1606445238000
             * price : 35.6
             * status : 1
             * ticket : 73
             */

            private int addressId;
            private long id;
            private long orderTime;
            private String price;
            private int status;
            private String ticket;

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(long orderTime) {
                this.orderTime = orderTime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTicket() {
                return ticket;
            }

            public void setTicket(String ticket) {
                this.ticket = ticket;
            }
        }

        public static class OrderDetailBean {
            /**
             * id : 163
             * number : 1
             * pic : /uploadFiles/img/2020-11-25/1606298563347.jpg
             * price : 11
             * productId : 61
             * productName : 雪碧
             * salesId : 1606445237924
             * specificationId : 320
             * specificationName : 2323
             * ticket : 23
             */

            private int id;
            private int number;
            private String pic;
            private String price;
            private int productId;
            private String productName;
            private long salesId;
            private int specificationId;
            private String specificationName;
            private String ticket;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public long getSalesId() {
                return salesId;
            }

            public void setSalesId(long salesId) {
                this.salesId = salesId;
            }

            public int getSpecificationId() {
                return specificationId;
            }

            public void setSpecificationId(int specificationId) {
                this.specificationId = specificationId;
            }

            public String getSpecificationName() {
                return specificationName;
            }

            public void setSpecificationName(String specificationName) {
                this.specificationName = specificationName;
            }

            public String getTicket() {
                return ticket;
            }

            public void setTicket(String ticket) {
                this.ticket = ticket;
            }
        }
    }
}
