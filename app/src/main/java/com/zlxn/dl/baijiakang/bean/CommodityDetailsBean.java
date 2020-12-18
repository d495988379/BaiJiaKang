package com.zlxn.dl.baijiakang.bean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/12 9:39
 */
public class CommodityDetailsBean {

    /**
     * product : {"categoryName":"分类测试22","id":33,"isChose":1,"originalPrice":0,"pic":"/uploadFiles/img/2020-11-19/1605754350501.jpg","price":0,"productCategoryId":22,"productContent":"","productName":"尖叫","productNumber":"000033","status":1,"unit":"元","updateOperatorId":1,"updateTime":1605754367000}
     * productNumber : 0
     * specification : [{"id":1,"originalPrice":4,"price":4,"productId":33,"specification":"瓶","version":1},{"id":2,"originalPrice":55,"price":60,"productId":33,"specification":"盒","version":1}]
     */

    private ProductBean product;
    private int productNumber;
    private List<SpecificationBean> specification;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public List<SpecificationBean> getSpecification() {
        return specification;
    }

    public void setSpecification(List<SpecificationBean> specification) {
        this.specification = specification;
    }

    public static class ProductBean {
        /**
         * categoryName : 分类测试22
         * id : 33
         * isChose : 1
         * originalPrice : 0
         * pic : /uploadFiles/img/2020-11-19/1605754350501.jpg
         * price : 0
         * productCategoryId : 22
         * productContent :
         * productName : 尖叫
         * productNumber : 000033
         * status : 1
         * unit : 元
         * updateOperatorId : 1
         * updateTime : 1605754367000
         */

        private String categoryName;
        private int id;
        private int isChose;
        private String originalPrice;
        private String pic;
        private String price;
        private int productCategoryId;
        private String productContent;
        private String productName;
        private String productNumber;
        private int status;
        private String unit;
        private int cardRoll;
        private int updateOperatorId;
        private long updateTime;
        private String rewind;

        public String getRewind() {
            return rewind;
        }

        public void setRewind(String rewind) {
            this.rewind = rewind;
        }

        public int getCardRoll() {
            return cardRoll;
        }

        public void setCardRoll(int cardRoll) {
            this.cardRoll = cardRoll;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsChose() {
            return isChose;
        }

        public void setIsChose(int isChose) {
            this.isChose = isChose;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
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

        public int getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(int productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getProductContent() {
            return productContent;
        }

        public void setProductContent(String productContent) {
            this.productContent = productContent;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getUpdateOperatorId() {
            return updateOperatorId;
        }

        public void setUpdateOperatorId(int updateOperatorId) {
            this.updateOperatorId = updateOperatorId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class SpecificationBean {
        /**
         * id : 1
         * originalPrice : 4
         * price : 4
         * productId : 33
         * specification : 瓶
         * version : 1
         */

        private int id;
        private String originalPrice;
        private String price;
        private int productId;
        private String specification;
        private int version;
        private int cardRoll;
        private String rewind;
        private String municipalityAgency;
        private String provincialCapital;
        private String deputyProvincialCapital;
        private String prefectureLevelCity;
        private String municipalityAgencyCounties;
        private String provincialCapitalCounties;
        private String deputyProvincialCapitalCounties;
        private String prefectureLevelCityCounties;
        private String goldPrice;
        private String silverPrice;
        private String pukaPrice;

        public String getGoldPrice() {
            return goldPrice;
        }

        public void setGoldPrice(String goldPrice) {
            this.goldPrice = goldPrice;
        }

        public String getSilverPrice() {
            return silverPrice;
        }

        public void setSilverPrice(String silverPrice) {
            this.silverPrice = silverPrice;
        }

        public String getPukaPrice() {
            return pukaPrice;
        }

        public void setPukaPrice(String pukaPrice) {
            this.pukaPrice = pukaPrice;
        }

        public String getMunicipalityAgency() {
            return municipalityAgency;
        }

        public void setMunicipalityAgency(String municipalityAgency) {
            this.municipalityAgency = municipalityAgency;
        }

        public String getProvincialCapital() {
            return provincialCapital;
        }

        public void setProvincialCapital(String provincialCapital) {
            this.provincialCapital = provincialCapital;
        }

        public String getDeputyProvincialCapital() {
            return deputyProvincialCapital;
        }

        public void setDeputyProvincialCapital(String deputyProvincialCapital) {
            this.deputyProvincialCapital = deputyProvincialCapital;
        }

        public String getPrefectureLevelCity() {
            return prefectureLevelCity;
        }

        public void setPrefectureLevelCity(String prefectureLevelCity) {
            this.prefectureLevelCity = prefectureLevelCity;
        }

        public String getMunicipalityAgencyCounties() {
            return municipalityAgencyCounties;
        }

        public void setMunicipalityAgencyCounties(String municipalityAgencyCounties) {
            this.municipalityAgencyCounties = municipalityAgencyCounties;
        }

        public String getProvincialCapitalCounties() {
            return provincialCapitalCounties;
        }

        public void setProvincialCapitalCounties(String provincialCapitalCounties) {
            this.provincialCapitalCounties = provincialCapitalCounties;
        }

        public String getDeputyProvincialCapitalCounties() {
            return deputyProvincialCapitalCounties;
        }

        public void setDeputyProvincialCapitalCounties(String deputyProvincialCapitalCounties) {
            this.deputyProvincialCapitalCounties = deputyProvincialCapitalCounties;
        }

        public String getPrefectureLevelCityCounties() {
            return prefectureLevelCityCounties;
        }

        public void setPrefectureLevelCityCounties(String prefectureLevelCityCounties) {
            this.prefectureLevelCityCounties = prefectureLevelCityCounties;
        }

        public String getRewind() {
            return rewind;
        }

        public void setRewind(String rewind) {
            this.rewind = rewind;
        }

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

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
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

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
