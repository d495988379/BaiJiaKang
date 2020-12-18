package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/13 14:41
 */
public class PjListBean {

    /**
     * total : 3
     * data : [{"id":4,"salesId":2,"productId":3,"userId":3,"evaluation":"评论一下3","pic":"/uploadFiles/2020-11-11/tengyun.jpg","star":2,"uspic":"/uploadFiles/2020-11-11/timg.jpg","wechatName":"测试"},{"id":5,"salesId":2,"productId":3,"userId":4,"evaluation":"评论一下","pic":"/uploadFiles/2020-11-11/tengyun.jpg","star":2,"uspic":"/uploadFiles/2020-11-11/timg.jpg","wechatName":"测试"},{"id":6,"salesId":2,"productId":3,"userId":4,"evaluation":"评论一下","pic":"/uploadFiles/2020-11-11/tengyun.jpg","star":2,"uspic":"/uploadFiles/2020-11-11/timg.jpg","wechatName":"测试"}]
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
         * id : 4
         * salesId : 2
         * productId : 3
         * userId : 3
         * evaluation : 评论一下3
         * pic : /uploadFiles/2020-11-11/tengyun.jpg
         * star : 2
         * uspic : /uploadFiles/2020-11-11/timg.jpg
         * wechatName : 测试
         */

        private int id;
        private long salesId;
        private int productId;
        private int userId;//0代理商  1用户
        private String evaluation;
        private String pic;
        private int star;
        private String uspic;
        private String wechatName;
        private String usName;
        private String stName;
        private String stpic;

        public String getUsName() {
            return usName;
        }

        public void setUsName(String usName) {
            this.usName = usName;
        }

        public String getStName() {
            return stName;
        }

        public void setStName(String stName) {
            this.stName = stName;
        }

        public String getStpic() {
            return stpic;
        }

        public void setStpic(String stpic) {
            this.stpic = stpic;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getUspic() {
            return uspic;
        }

        public void setUspic(String uspic) {
            this.uspic = uspic;
        }

        public String getWechatName() {
            return wechatName;
        }

        public void setWechatName(String wechatName) {
            this.wechatName = wechatName;
        }
    }
}
