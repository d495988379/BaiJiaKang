package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/3 15:22
 */
public class JyListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 天津市东丽区机场路1198号
         * cityName : 天津市
         * gaslogobig : https://static.czb365.com/1578453239615.jpg?x-oss-process=image/resize,m_lfit,h_420,w_630/format,png
         * gaslogosmall : https://static.czb365.com/1578453239762.jpg?x-oss-process=image/resize,m_lfit,h_200,w_200/format,png
         * provinceName : 天津市
         * gasName : 航油加油一站（机场路站）
         * countyName : 东丽区
         * id : HY000009171
         * gasid : HY000009171
         * longitude : 117.3374
         * latitude : 39.1177
         * distance : 2142.534709785771
         * oilPriceList : [{"oilNo":"92","oilName":"92#","oilType":"1","priceYfq":4.87,"priceGun":5.34,"priceOfficial":5.54,"gunNos":"3,4,5,6,13,14,15,16"}]
         * oilNo : null
         * oilName : null
         * oilType : null
         * priceYfqSearch : null
         * priceGunSearch : null
         * priceOfficialSearch : null
         * gaschannel : CZB
         */

        private String address;
        private String cityName;
        private String gaslogobig;
        private String gaslogosmall;
        private String provinceName;
        private String gasName;
        private String countyName;
        private String id;
        private String gasid;
        private double longitude;
        private double latitude;
        private String distance;
        private Object oilNo;
        private Object oilName;
        private Object oilType;
        private Object priceYfqSearch;
        private Object priceGunSearch;
        private Object priceOfficialSearch;
        private String gaschannel;
        private List<OilPriceListBean> oilPriceList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getGaslogobig() {
            return gaslogobig;
        }

        public void setGaslogobig(String gaslogobig) {
            this.gaslogobig = gaslogobig;
        }

        public String getGaslogosmall() {
            return gaslogosmall;
        }

        public void setGaslogosmall(String gaslogosmall) {
            this.gaslogosmall = gaslogosmall;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getGasName() {
            return gasName;
        }

        public void setGasName(String gasName) {
            this.gasName = gasName;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGasid() {
            return gasid;
        }

        public void setGasid(String gasid) {
            this.gasid = gasid;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public Object getOilNo() {
            return oilNo;
        }

        public void setOilNo(Object oilNo) {
            this.oilNo = oilNo;
        }

        public Object getOilName() {
            return oilName;
        }

        public void setOilName(Object oilName) {
            this.oilName = oilName;
        }

        public Object getOilType() {
            return oilType;
        }

        public void setOilType(Object oilType) {
            this.oilType = oilType;
        }

        public Object getPriceYfqSearch() {
            return priceYfqSearch;
        }

        public void setPriceYfqSearch(Object priceYfqSearch) {
            this.priceYfqSearch = priceYfqSearch;
        }

        public Object getPriceGunSearch() {
            return priceGunSearch;
        }

        public void setPriceGunSearch(Object priceGunSearch) {
            this.priceGunSearch = priceGunSearch;
        }

        public Object getPriceOfficialSearch() {
            return priceOfficialSearch;
        }

        public void setPriceOfficialSearch(Object priceOfficialSearch) {
            this.priceOfficialSearch = priceOfficialSearch;
        }

        public String getGaschannel() {
            return gaschannel;
        }

        public void setGaschannel(String gaschannel) {
            this.gaschannel = gaschannel;
        }

        public List<OilPriceListBean> getOilPriceList() {
            return oilPriceList;
        }

        public void setOilPriceList(List<OilPriceListBean> oilPriceList) {
            this.oilPriceList = oilPriceList;
        }

        public static class OilPriceListBean {
            /**
             * oilNo : 92
             * oilName : 92#
             * oilType : 1
             * priceYfq : 4.87
             * priceGun : 5.34
             * priceOfficial : 5.54
             * gunNos : 3,4,5,6,13,14,15,16
             */

            private String oilNo;
            private String oilName;
            private String oilType;
            private String priceYfq;
            private String priceGun;
            private String priceOfficial;
            private String gunNos;

            public String getOilNo() {
                return oilNo;
            }

            public void setOilNo(String oilNo) {
                this.oilNo = oilNo;
            }

            public String getOilName() {
                return oilName;
            }

            public void setOilName(String oilName) {
                this.oilName = oilName;
            }

            public String getOilType() {
                return oilType;
            }

            public void setOilType(String oilType) {
                this.oilType = oilType;
            }

            public String getPriceYfq() {
                return priceYfq;
            }

            public void setPriceYfq(String priceYfq) {
                this.priceYfq = priceYfq;
            }

            public String getPriceGun() {
                return priceGun;
            }

            public void setPriceGun(String priceGun) {
                this.priceGun = priceGun;
            }

            public String getPriceOfficial() {
                return priceOfficial;
            }

            public void setPriceOfficial(String priceOfficial) {
                this.priceOfficial = priceOfficial;
            }

            public String getGunNos() {
                return gunNos;
            }

            public void setGunNos(String gunNos) {
                this.gunNos = gunNos;
            }
        }
    }
}
