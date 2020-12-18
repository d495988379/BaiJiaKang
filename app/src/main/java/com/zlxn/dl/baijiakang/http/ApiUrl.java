package com.zlxn.dl.baijiakang.http;


import com.zlxn.dl.baijiakang.bean.AddOrderBean;
import com.zlxn.dl.baijiakang.bean.AddressBean;
import com.zlxn.dl.baijiakang.bean.BalanceRecordsBean;
import com.zlxn.dl.baijiakang.bean.BannerListBean;
import com.zlxn.dl.baijiakang.bean.BasicJcBean;
import com.zlxn.dl.baijiakang.bean.BasicListBean;
import com.zlxn.dl.baijiakang.bean.CancelOrderBean;
import com.zlxn.dl.baijiakang.bean.ChildBean;
import com.zlxn.dl.baijiakang.bean.CommodityDetailsBean;
import com.zlxn.dl.baijiakang.bean.CommodityListBean;
import com.zlxn.dl.baijiakang.bean.DlsListBean;
import com.zlxn.dl.baijiakang.bean.JyDetailsBean;
import com.zlxn.dl.baijiakang.bean.JyListBean;
import com.zlxn.dl.baijiakang.bean.JyOrderListBean;
import com.zlxn.dl.baijiakang.bean.JyPayBean;
import com.zlxn.dl.baijiakang.bean.LoginBean;
import com.zlxn.dl.baijiakang.bean.LqJfBean;
import com.zlxn.dl.baijiakang.bean.MyUserBean;
import com.zlxn.dl.baijiakang.bean.OneClassBean;
import com.zlxn.dl.baijiakang.bean.OrderDetailsBean;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.bean.PjListBean;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.bean.UploadPicBean;
import com.zlxn.dl.baijiakang.bean.UserListBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author DL
 * @name RetrofitTest
 * @class 接口参数（修改）
 * @time 2019/10/30 17:44
 */
public interface ApiUrl {


    //登陆
    @POST(Constans.LOGIN_URL)
    Observable<BaseResponse<LoginBean>> login(@Query("loginType") String loginType, @Query("password") String password, @Query("userName") String userName);

    //轮播图
    @POST(Constans.BANNER_LIST)
    Observable<BaseResponse<List<BannerListBean>>> bannerList(@Query("type") String type, @Query("token") String token);

    //一级分类
    @POST(Constans.ONE_CLASS)
    Observable<BaseResponse<List<OneClassBean>>> oneClass(@Query("type") String type, @Query("token") String token);

    //一级分类子分类
    @POST(Constans.CHILD_CLASS)
    Observable<BaseResponse<List<ChildBean>>> childClass(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //通过分类获取商品
    @POST(Constans.CLASS_COMMODITY)
    Observable<BaseResponse<CommodityListBean>> classCommodity(@Query("type") String type, @Query("page") String page, @Query("rows") String rows, @Query("id") String id, @Query("token") String token);

    //商品
    @POST(Constans.COMMODITY_LIST)
    Observable<BaseResponse<CommodityListBean>> commodityList(@Query("type") String type, @Query("page") String page, @Query("rows") String rows, @Query("token") String token);

    //商品
    @POST(Constans.SEARCH_COMMODITY)
    Observable<BaseResponse<CommodityListBean>> searchCommodity(@Query("type") String type, @Query("name") String name, @Query("page") String page, @Query("rows") String rows, @Query("token") String token);

    //添加收货地址
    @POST(Constans.ADD_ADDRESS)
    Observable<BaseResponse<String>> addAddress(@Query("type") String type, @Query("userName") String userName, @Query("phone") String phone, @Query("area") String area, @Query("detailArea") String detailArea, @Query("token") String token);

    //收货地址
    @POST(Constans.ADDRESS_LIST)
    Observable<BaseResponse<AddressBean>> addressList(@Query("type") String type, @Query("token") String token);

    //删除地址
    @POST(Constans.DELETE_ADDRESS)
    Observable<BaseResponse<String>> deleteAddress(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //默认地址
    @POST(Constans.DEFAULT_ADDRESS)
    Observable<BaseResponse<String>> defaultAddress(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //商品详情
    @POST(Constans.COMMODITY_DETAILS)
    Observable<BaseResponse<CommodityDetailsBean>> commodityDetails(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //添加商品到购物车
    @POST(Constans.ADD_CAR)
    Observable<BaseResponse<String>> addCar(@Query("type") String type, @Query("productId") String productId, @Query("number") String number, @Query("specificationId") String specificationId, @Query("ticket") String ticket, @Query("token") String token);

    //购物车列表
    @POST(Constans.CAR_LIST)
    Observable<BaseResponse<ShopCarBean>> carList(@Query("type") String type, @Query("token") String token);

    //删除购物车商品
    @POST(Constans.DELETE_CAR)
    Observable<BaseResponse<String>> deleteCar(@Query("type") String type, @Query("ids") String ids, @Query("token") String token);


    //商品++
    @POST(Constans.ADD_COMM_CAR)
    Observable<BaseResponse<String>> addComm(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //商品--
    @POST(Constans.JIAN_COMM_CAR)
    Observable<BaseResponse<String>> jianComm(@Query("type") String type, @Query("id") String id, @Query("token") String token);

    //新增订单
    @POST(Constans.ADD_ORDER)
    Observable<BaseResponse<AddOrderBean>> addOrder(@Query("type") String type, @Query("payType") String payType, @Query("number") String number, @Query("specificationIds") String specificationId, @Query("shoppingType") String shoppingType, @Query("addressId") String addressId, @Query("allPrice") String allPrice, @Query("ids") String ids, @Query("ticket") String ticket, @Query("token") String token);

    //评价列表
    @POST(Constans.PJ_LIST)
    Observable<BaseResponse<PjListBean>> pjList(@Query("type") String type, @Query("page") String page, @Query("rows") String rows, @Query("productId") String productId, @Query("token") String token);

    //订单列表
    @POST(Constans.ORDER_LIST)
    Observable<BaseResponse<OrderListBean>> orderList(@Query("type") String type, @Query("orderType") String orderType, @Query("page") String page, @Query("rows") String rows, @Query("token") String token);

    //取消订单
    @POST(Constans.CANCEL_ORDER)
    Observable<BaseResponse<CancelOrderBean>> cancelOrder(@Query("type") String type, @Query("orderNumber") String orderNumber, @Query("token") String token);

    //订单详情
    @POST(Constans.ORDER_DETAILS)
    Observable<BaseResponse<OrderDetailsBean>> orderDetails(@Query("type") String type, @Query("orderNumber") String orderNumber, @Query("token") String token);

    //确认收货
    @POST(Constans.CONFIRM_TAKE)
    Observable<BaseResponse<String>> confirmTake(@Query("type") String type, @Query("orderNumber") String orderNumber, @Query("token") String token);

    //新增评论
    @POST(Constans.ADD_EVALUATION)
    Observable<BaseResponse<String>> addEvaluation(@Query("type") String type, @Query("evaluation") String evaluation, @Query("pic") String pic, @Query("productId") String productId, @Query("salesId") String salesId, @Query("star") String star, @Query("token") String token);

    //上传图片
    @POST(Constans.PIC_UPLOAD)
    Observable<BaseResponse<UploadPicBean>> uploadPic(@Body MultipartBody file);


    //修改登录密码
    @POST(Constans.SET_LOGIN_PWD)
    Observable<BaseResponse<String>> setLoginPwd(@Query("type") String type, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword, @Query("token") String token);

    //修改支付密码
    @POST(Constans.SET_PAY_PWD)
    Observable<BaseResponse<String>> setPayPwd(@Query("type") String type, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword, @Query("token") String token);

    //地区管理
    @POST(Constans.REGION_MANAGE)
    Observable<BaseResponse<String>> regionManage(@Query("token") String token);

    //查询用户礼券积分信息
    @POST(Constans.SEARCH_LQ_JF)
    Observable<BaseResponse<LqJfBean>> searchLqJf(@Query("type") String type, @Query("token") String token);

    //我的用户
    @POST(Constans.MY_USER)
    Observable<BaseResponse<MyUserBean>> myUser(@Query("type") String type, @Query("token") String token);

    //普通用户详情
    @POST(Constans.USER_DETAILS)
    Observable<BaseResponse<List<UserListBean>>> userDetails(@Query("status") String status, @Query("token") String token);

    //代理商用户详情
    @POST(Constans.DLS_DETAILS)
    Observable<BaseResponse<List<DlsListBean>>> dlsDetails(@Query("status") String status, @Query("token") String token);

    //返佣明细-基础字段
    @POST(Constans.BASIC_JC)
    Observable<BaseResponse<BasicJcBean>> basicJc(@Query("type") String type, @Query("token") String token);

    //代理商用户详情
    @POST(Constans.BASIC_LIST)
    Observable<BaseResponse<BasicListBean>> basicList(@Query("type") String type, @Query("page") String page, @Query("rows") String rows, @Query("token") String token);

    //加油列表
    @POST(Constans.JY_LIST)
    Observable<BaseResponse<JyListBean>> jyList(@Query("latitude") String latitude, @Query("longitude") String longitude, @Query("oilType") String oilType,
                                                @Query("pageNo") String pageNo, @Query("pageSize") String pageSize, @Query("priority") String priority, @Query("token") String token);

    //油站详情
    @POST(Constans.JY_DETAILS)
    Observable<BaseResponse<JyDetailsBean>> jyDetails(@Query("gasId") String gasId,@Query("mobile") String mobile, @Query("token") String token);

    //查询余额列表
    @POST(Constans.CHECK_RECORDS)
    Observable<BaseResponse<BalanceRecordsBean>> checkRecords(@Query("type") String type, @Query("page") String page, @Query("rows") String rows, @Query("token") String token);

    //支付
    @POST(Constans.PAY_URL)
    Observable<BaseResponse<String>> payUrl(@Query("type") String type, @Query("password") String password, @Query("payType") String payType, @Query("salesId") String salesId, @Query("token") String token);

    //下单前置授权
    @POST(Constans.PETROL_AUTH_URL)
    Observable<BaseResponse<JyPayBean>> petrolAuth(@Query("gasId") String gasId, @Query("mobile") String mobile, @Query("oilGun") String oilGun, @Query("oilNum") String oilNum, @Query("token") String token);

    @POST(Constans.USER_JY_ORDER)
    Observable<BaseResponse<JyOrderListBean>> userJyOrder(@Query("mobile") String mobile, @Query("currentPageNo") String currentPageNo, @Query("pageSize") String pageSize, @Query("token") String token);

    @POST(Constans.WITHDRAWAL_URL)
    Observable<BaseResponse<String>> withdrawalUrl(@Query("type") String type , @Query("withdrawalMoney") String withdrawalMoney, @Query("token") String token);
}
