package com.zlxn.dl.baijiakang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.DataHelper;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.OrderDetailsAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.CancelOrderBean;
import com.zlxn.dl.baijiakang.bean.OrderDetailsBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.DateUtil;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.OnPasswordInputFinish;
import com.zlxn.dl.baijiakang.view.PopEnterPassword;

import java.text.NumberFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 14:26
 */
public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.addressName)
    TextView addressName;
    @BindView(R.id.addressPhone)
    TextView addressPhone;
    @BindView(R.id.addressAddress)
    TextView addressAddress;
    @BindView(R.id.detailsRv)
    RecyclerView detailsRv;
    @BindView(R.id.orderBtn2)
    Button orderBtn2;
    @BindView(R.id.orderBtn1)
    Button orderBtn1;
    @BindView(R.id.orderDetailsNum)
    TextView orderDetailsNum;
    @BindView(R.id.orderDetailsTime)
    TextView orderDetailsTime;
    @BindView(R.id.orderDetailsMoney)
    TextView orderDetailsMoney;
    @BindView(R.id.orderDetailsLin)
    LinearLayout orderDetailsLin;
    @BindView(R.id.orderDetailsPayTime)
    TextView orderDetailsPayTime;
    @BindView(R.id.orderDetailsPayLin)
    LinearLayout orderDetailsPayLin;
    @BindView(R.id.orderDetailsFhTime)
    TextView orderDetailsFhTime;
    @BindView(R.id.orderDetailsFhLin)
    LinearLayout orderDetailsFhLin;
    @BindView(R.id.orderDetailsQsTime)
    TextView orderDetailsQsTime;
    @BindView(R.id.orderDetailsQsLin)
    LinearLayout orderDetailsQsLin;
    @BindView(R.id.orderDetailsTopLin)
    LinearLayout orderDetailsTopLin;
    private MyObserver<OrderDetailsBean> myObserver;
    private OrderDetailsAdapter adapter;
    private NumberFormat nf;

    @Override
    protected void initView() {
        setContentView(R.layout.act_order_details);
    }

    @Override
    protected void initData() {
        commonBar.setBackgroundResource(R.color.main_color);

        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);

        String state = getIntent().getStringExtra("state");

        detailsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        myObserver = new MyObserver<OrderDetailsBean>(OrderDetailsActivity.this, true) {
            @Override
            public void onSuccess(OrderDetailsBean result) {
                addressName.setText(result.getOrder().getUserName());
                addressPhone.setText(result.getOrder().getPhone());
                addressAddress.setText(result.getOrder().getArea() + result.getOrder().getDetailArea());
                orderDetailsNum.setText(result.getOrder().getId() + "");
                orderDetailsTime.setText(DateUtil.getDateToString(result.getOrder().getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
                orderDetailsMoney.setText("实付款：¥ " + result.getOrder().getPrice() + "   券总额：¥ " + result.getOrder().getTicket());


                if (state.equals("1")) {
                    orderDetailsLin.setVisibility(View.VISIBLE);
                    orderBtn1.setVisibility(View.VISIBLE);
                    orderBtn2.setVisibility(View.VISIBLE);
                    orderBtn1.setText("立即付款");
                    orderBtn2.setText("取消订单");
                    orderDetailsPayLin.setVisibility(View.GONE);
                    orderDetailsQsLin.setVisibility(View.GONE);
                    orderDetailsFhLin.setVisibility(View.GONE);
                    orderBtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RetrofitUtils.getApiUrl().cancelOrder(DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") + "", getIntent().getStringExtra("id"), DataHelper.getStringSF(OrderDetailsActivity.this, "token"))
                                    .compose(RxHelper.observableIO2Main(OrderDetailsActivity.this))
                                    .subscribe(new MyObserver<CancelOrderBean>(OrderDetailsActivity.this, true) {
                                        @Override
                                        public void onSuccess(CancelOrderBean result) {
                                            ToastUtils.show(OrderDetailsActivity.this, "取消成功");
                                            DataHelper.setBooleanSF(OrderDetailsActivity.this, "isSet", true);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                            if (e != null) {
                                                ToastUtils.show(OrderDetailsActivity.this, errorCode);
                                            } else {
                                                if (errorCode.equals("401")) {
                                                    Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    ToastUtils.show(OrderDetailsActivity.this, errorMsg);
                                                }
                                            }
                                        }
                                    });

                        }
                    });

                    orderBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new XPopup.Builder(OrderDetailsActivity.this)
                                    .enableDrag(true)
                                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                                    .asCustom(new PayPopupView(OrderDetailsActivity.this,result.getOrder().getId() + "",result.getOrder().getPrice()))/*.enableDrag(false)*/
                                    .show();

                        }
                    });

                } else if (state.equals("2")) {
                    orderDetailsLin.setVisibility(View.VISIBLE);
                    orderBtn1.setVisibility(View.VISIBLE);
                    orderBtn2.setVisibility(View.GONE);
                    orderBtn1.setText("等待发货");
                    orderDetailsPayLin.setVisibility(View.VISIBLE);
                    orderDetailsQsLin.setVisibility(View.GONE);
                    orderDetailsFhLin.setVisibility(View.GONE);
                    if (result.getOrder().getPaymentTime() != null) {
                        orderDetailsPayTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getPaymentTime())), "yyyy-MM-dd HH:mm:ss"));
                    }

                } else if (state.equals("3")) {
                    orderDetailsLin.setVisibility(View.VISIBLE);
                    orderBtn1.setVisibility(View.VISIBLE);
                    orderBtn2.setVisibility(View.GONE);
                    orderBtn1.setText("确认收货");

                    orderDetailsPayLin.setVisibility(View.VISIBLE);
                    orderDetailsQsLin.setVisibility(View.GONE);
                    orderDetailsFhLin.setVisibility(View.VISIBLE);
                    if (result.getOrder().getPaymentTime() != null) {
                        orderDetailsPayTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getPaymentTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (result.getOrder().getShippingTime() != null) {
                        orderDetailsFhTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getShippingTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    orderBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RetrofitUtils.getApiUrl().confirmTake(DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") + "", getIntent().getStringExtra("id"), DataHelper.getStringSF(OrderDetailsActivity.this, "token"))
                                    .compose(RxHelper.observableIO2Main(OrderDetailsActivity.this))
                                    .subscribe(new MyObserver<String>(OrderDetailsActivity.this, true) {
                                        @Override
                                        public void onSuccess(String result) {
                                            ToastUtils.show(OrderDetailsActivity.this, "收货成功");
                                            DataHelper.setBooleanSF(OrderDetailsActivity.this, "isSet", true);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                            if (e != null) {
                                                ToastUtils.show(OrderDetailsActivity.this, errorCode);
                                            } else {
                                                if (errorCode.equals("401")) {
                                                    Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    ToastUtils.show(OrderDetailsActivity.this, errorMsg);
                                                }
                                            }
                                        }
                                    });
                        }
                    });
                } else if (state.equals("4")) {
                    orderDetailsLin.setVisibility(View.VISIBLE);
                    orderBtn1.setVisibility(View.VISIBLE);
                    orderBtn2.setVisibility(View.GONE);
                    orderBtn1.setText("评价");

                    orderDetailsPayLin.setVisibility(View.VISIBLE);
                    orderDetailsQsLin.setVisibility(View.VISIBLE);
                    orderDetailsFhLin.setVisibility(View.VISIBLE);

                    if (result.getOrder().getPaymentTime() != null) {
                        orderDetailsPayTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getPaymentTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (result.getOrder().getShippingTime() != null) {
                        orderDetailsFhTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getShippingTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (result.getOrder().getReceivingTime() != null) {
                        orderDetailsQsTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getReceivingTime())), "yyyy-MM-dd HH:mm:ss"));
                    }

                    orderBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(OrderDetailsActivity.this, ReleasePjActivity.class)
                                    .putExtra("id", getIntent().getStringExtra("id"))
                                    .putExtra("location", "orderDetails")
                                    .putExtra("details", getIntent().getStringExtra("details")), 1);
                        }
                    });
                } else if (state.equals("5")) {
                    orderDetailsLin.setVisibility(View.GONE);
                    orderBtn1.setVisibility(View.GONE);
                    orderBtn2.setVisibility(View.GONE);
                    orderDetailsPayLin.setVisibility(View.VISIBLE);
                    orderDetailsQsLin.setVisibility(View.VISIBLE);
                    orderDetailsFhLin.setVisibility(View.VISIBLE);

                    if (result.getOrder().getPaymentTime() != null) {
                        orderDetailsPayTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getPaymentTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (result.getOrder().getShippingTime() != null) {
                        orderDetailsFhTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getShippingTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (result.getOrder().getReceivingTime() != null) {
                        orderDetailsQsTime.setText(DateUtil.getDateToString(Long.parseLong(nf.format(result.getOrder().getReceivingTime())), "yyyy-MM-dd HH:mm:ss"));
                    }
                }else if (state.equals("7")){
                    orderDetailsLin.setVisibility(View.GONE);
                    orderBtn1.setVisibility(View.GONE);
                    orderBtn2.setVisibility(View.GONE);
                    orderDetailsPayLin.setVisibility(View.GONE);
                    orderDetailsQsLin.setVisibility(View.GONE);
                    orderDetailsFhLin.setVisibility(View.GONE);
                }

                if (result.getOrderDetail() != null && result.getOrderDetail().size() > 0) {
                    adapter = new OrderDetailsAdapter(OrderDetailsActivity.this, R.layout.item_confirm, result.getOrderDetail());
                    detailsRv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(OrderDetailsActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(OrderDetailsActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().orderDetails(DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") + "", getIntent().getStringExtra("id"), DataHelper.getStringSF(this, "token"))
                .compose(RxHelper.observableIO2Main(this))
                .subscribe(myObserver);


    }

    @Override
    protected void setListen() {

    }

    public class PayPopupView extends BottomPopupView {

        private String id;
        private String price;

        public PayPopupView(@NonNull Context context,String id,String price) {
            super(context);
            this.id = id;
            this.price = price;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_pay;
        }

        @Override
        protected void onCreate() {
            LinearLayout rel4 = findViewById(R.id.payTypeRel4);
            LinearLayout rel3 = findViewById(R.id.payTypeRel3);
            LinearLayout rel2 = findViewById(R.id.payTypeRel2);
            LinearLayout rel1 = findViewById(R.id.payTypeRel1);

            if (DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") == 1) {//代理商
                rel4.setVisibility(View.VISIBLE);
            } else {
                rel4.setVisibility(View.GONE);
            }

            rel1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            rel2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });


            rel4.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    RetrofitUtils.getApiUrl().payUrl(DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") + "",
                            "", "4", id,
                            DataHelper.getStringSF(OrderDetailsActivity.this, "token"))
                            .compose(RxHelper.observableIO2Main(OrderDetailsActivity.this))
                            .subscribe(new MyObserver<String>(OrderDetailsActivity.this, true) {
                                @Override
                                public void onSuccess(String result) {
                                    ToastUtils.show(OrderDetailsActivity.this, "支付成功");
                                    DataHelper.setBooleanSF(OrderDetailsActivity.this, "isSet", true);
                                    finish();
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(OrderDetailsActivity.this, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            ToastUtils.show(OrderDetailsActivity.this, errorMsg);
                                        }
                                    }
                                }
                            });
                }
            });

            rel3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (!DataHelper.getBooleanSF(OrderDetailsActivity.this, "isPayPwd")) {
                        ToastUtils.show(OrderDetailsActivity.this, "请设置支付密码");
                        return;
                    }
                    final PopEnterPassword popEnterPassword = new PopEnterPassword(OrderDetailsActivity.this, "支付金额",Double.parseDouble(price));
                    popEnterPassword.getPwdView().setOnFinishInput(new OnPasswordInputFinish() {
                        @Override
                        public void inputFinish(String password) {
                            RetrofitUtils.getApiUrl().payUrl(DataHelper.getIntergerSF(OrderDetailsActivity.this, "userType") + "",
                                    password, "3", id,
                                    DataHelper.getStringSF(OrderDetailsActivity.this, "token"))
                                    .compose(RxHelper.observableIO2Main(OrderDetailsActivity.this))
                                    .subscribe(new MyObserver<String>(OrderDetailsActivity.this, true) {
                                        @Override
                                        public void onSuccess(String result) {
                                            ToastUtils.show(OrderDetailsActivity.this, "支付成功");
                                            DataHelper.setBooleanSF(OrderDetailsActivity.this, "isSet", true);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                            if (e != null) {
                                                ToastUtils.show(OrderDetailsActivity.this, errorCode);
                                            } else {
                                                if (errorCode.equals("401")) {
                                                    Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    ToastUtils.show(OrderDetailsActivity.this, errorMsg);
                                                }
                                            }
                                        }
                                    });
                        }
                    });

                    popEnterPassword.showAtLocation(orderDetailsTopLin,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

                }
            });

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                orderDetailsLin.setVisibility(View.GONE);
                orderBtn1.setVisibility(View.GONE);
                orderBtn2.setVisibility(View.GONE);
            }
        }
    }

}
