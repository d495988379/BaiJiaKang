package com.zlxn.dl.baijiakang.bean;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/25 19:13
 */
public class LqJfBean {

    /**
     * data : {"id":2,"balance":25414,"userId":3,"ticket":5000,"points":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * balance : 25414
         * userId : 3
         * ticket : 5000
         * points : 0
         */

        private int id;
        private String balance;
        private int userId;
        private String ticket;
        private String points;
        private String dateAfterRecharging;
        private String makeMoney;


        public String getMakeMoney() {
            return makeMoney;
        }

        public void setMakeMoney(String makeMoney) {
            this.makeMoney = makeMoney;
        }

        public String getDateAfterRecharging() {
            return dateAfterRecharging;
        }

        public void setDateAfterRecharging(String dateAfterRecharging) {
            this.dateAfterRecharging = dateAfterRecharging;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }
}
