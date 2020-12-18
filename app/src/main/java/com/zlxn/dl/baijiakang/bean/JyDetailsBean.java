package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/7 10:00
 */
public class JyDetailsBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * businessHours : 24小时
         * gasId : HY000009171
         * gasName : 航油加油一站（机场路站）
         * oilPriceList : [{"gunNos":[{"gunNo":3},{"gunNo":4},{"gunNo":5},{"gunNo":6},{"gunNo":13},{"gunNo":14},{"gunNo":15},{"gunNo":16}],"oilName":"92#","oilNo":92,"oilType":1,"priceGun":"5.54","priceOfficial":"5.74","priceYfq":"5.00"}]
         */

        private String businessHours;
        private String gasId;
        private String gasName;
        private List<OilPriceListBean> oilPriceList;

        public String getBusinessHours() {
            return businessHours;
        }

        public void setBusinessHours(String businessHours) {
            this.businessHours = businessHours;
        }

        public String getGasId() {
            return gasId;
        }

        public void setGasId(String gasId) {
            this.gasId = gasId;
        }

        public String getGasName() {
            return gasName;
        }

        public void setGasName(String gasName) {
            this.gasName = gasName;
        }

        public List<OilPriceListBean> getOilPriceList() {
            return oilPriceList;
        }

        public void setOilPriceList(List<OilPriceListBean> oilPriceList) {
            this.oilPriceList = oilPriceList;
        }

        public static class OilPriceListBean {
            /**
             * gunNos : [{"gunNo":3},{"gunNo":4},{"gunNo":5},{"gunNo":6},{"gunNo":13},{"gunNo":14},{"gunNo":15},{"gunNo":16}]
             * oilName : 92#
             * oilNo : 92
             * oilType : 1
             * priceGun : 5.54
             * priceOfficial : 5.74
             * priceYfq : 5.00
             */

            private String oilName;
            private int oilNo;
            private int oilType;
            private String priceGun;
            private String priceOfficial;
            private String priceYfq;
            private List<GunNosBean> gunNos;

            public String getOilName() {
                return oilName;
            }

            public void setOilName(String oilName) {
                this.oilName = oilName;
            }

            public int getOilNo() {
                return oilNo;
            }

            public void setOilNo(int oilNo) {
                this.oilNo = oilNo;
            }

            public int getOilType() {
                return oilType;
            }

            public void setOilType(int oilType) {
                this.oilType = oilType;
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

            public String getPriceYfq() {
                return priceYfq;
            }

            public void setPriceYfq(String priceYfq) {
                this.priceYfq = priceYfq;
            }

            public List<GunNosBean> getGunNos() {
                return gunNos;
            }

            public void setGunNos(List<GunNosBean> gunNos) {
                this.gunNos = gunNos;
            }

            public static class GunNosBean {
                /**
                 * gunNo : 3
                 */

                private int gunNo;

                public int getGunNo() {
                    return gunNo;
                }

                public void setGunNo(int gunNo) {
                    this.gunNo = gunNo;
                }
            }
        }
    }
}
