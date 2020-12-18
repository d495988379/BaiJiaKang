package com.zlxn.dl.baijiakang.http;

/**
 * @author DL
 * @name RetrofitTest
 * @class 接口地址（修改）
 * @time 2019/10/30 17:34
 */
public class Constans {

    //设置默认超时时间
    public static final int DEFAULT_TIME = 10;

    //线上：http://39.99.202.81:8090
    //本地：http://172.16.0.231:8090

    public final static String BaseUrl = "http://39.99.202.81:8090";
    public final static String PicUrl = "http://39.99.202.81:8090";
    public final static String retrofit = "values/5";
    public final static String retrofitList = "values";


    //登录
    public static final String LOGIN_URL = BaseUrl  + "/login/login";

    //轮播图
    public static final String BANNER_LIST = BaseUrl + "/app/banner/appGetAll";

    //一级分类
    public static final String ONE_CLASS = BaseUrl + "/app/category/appGetAll";

    //通过一级分类获取子分类
    public static final String CHILD_CLASS = BaseUrl + "/app/category/appChild";

    //通过分类获取商品
    public static final String CLASS_COMMODITY = BaseUrl + "/app/product/getProductIsChoicedByCategory";

    //商品
    public static final String COMMODITY_LIST = BaseUrl + "/app/product/getProductIsChoiced";

    //模糊查询商品
    public static final String SEARCH_COMMODITY = BaseUrl + "/app/product/findProductAll";

    //添加收货地址
    public static final String ADD_ADDRESS = BaseUrl + "/address/addUserAddress";

    //收货地址
    public static final String ADDRESS_LIST = BaseUrl + "/address/findUserAddress";

    //删除地址
    public static final String DELETE_ADDRESS = BaseUrl + "/address/deleteUserAddress";

    //设置默认地址
    public static final String DEFAULT_ADDRESS = BaseUrl + "/address/confirmUserAddress";

    //商品详情
    public static final String COMMODITY_DETAILS = BaseUrl + "/app/product/getProductById";

    //添加到购物车
    public static final String ADD_CAR = BaseUrl + "/shopping/addShoppingCart";

    //购物车列表
    public static final String CAR_LIST = BaseUrl + "/shopping/shoppingCartList";

    //删除购物车商品
    public static final String DELETE_CAR = BaseUrl + "/shopping/deleteShoppingCart";

    //增加商品
    public static final String ADD_COMM_CAR = BaseUrl + "/shopping/addShoppingCartProductNumber";

    //删除商品
    public static final String JIAN_COMM_CAR = BaseUrl + "/shopping/decreaseShoppingCartProductNumber";

    //新增订单
    public static final String ADD_ORDER = BaseUrl + "/order/addOrder";

    //评价列表
    public static final String PJ_LIST = BaseUrl + "/order/evaluationList";

    //订单列表
    public static final String ORDER_LIST = BaseUrl + "/order/orderList";

    //取消订单
    public static final String CANCEL_ORDER = BaseUrl + "/order/cancelOrder";

    //订单详情
    public static final String ORDER_DETAILS = BaseUrl + "/order/orderDetail";

    //确认收货
    public static final String CONFIRM_TAKE = BaseUrl + "/order/confirmOrder";

    //新增评论
    public static final String ADD_EVALUATION = BaseUrl + "/order/addEvaluation";

    //图片上传
    public static final String PIC_UPLOAD = BaseUrl + "/upload/uploadFile";

    //修改登录密码
    public static final String SET_LOGIN_PWD = BaseUrl + "/user/updateUserPassword";

    //修改支付密码
    public static final String SET_PAY_PWD = BaseUrl + "/user/updateUserPayPassword";

    //地区管理
    public static final String REGION_MANAGE = BaseUrl + "/order/areaListAllByApp";

    //查询用户礼券积分信息
    public static final String SEARCH_LQ_JF = BaseUrl + "/user/findTicketAndPoints";

    //我的用户
    public static final String MY_USER = BaseUrl + "/app/myUsers/appGetList";

    //普通用户详情
    public static final String USER_DETAILS = BaseUrl + "/app/myUsers/appGetListUserDetail";

    //代理商用户详情
    public static final String DLS_DETAILS = BaseUrl + "/app/myUsers/appGetListMemberDetail";

    //返佣明细（基础字段）
    public static final String BASIC_JC = BaseUrl + "/app/myUsers/appMoneyBasic";

    //返佣明细（列表）
    public static final String BASIC_LIST = BaseUrl + "/app/myUsers/appMoneyBasicList";

    //加油列表
    public static final String JY_LIST = BaseUrl + "/oil/searchNearbyGasList";

    //油站详情
    public static final String JY_DETAILS = BaseUrl + "/oil/selGasListForID";

    //查询余额消费记录
    public static final String CHECK_RECORDS = BaseUrl + "/user/findBalanceConsumption";

    //支付
    public static final String PAY_URL = BaseUrl + "/order/orderPayment";

    //下单前置授权
    public static final String PETROL_AUTH_URL = BaseUrl + "/oil/petrolAuthUrl";

    //用户订单
    public static final String USER_JY_ORDER = BaseUrl + "/oil/orderList";

    //提现
    public static final String WITHDRAWAL_URL = BaseUrl + "/user/addWithdrawal";

}
