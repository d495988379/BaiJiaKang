package com.zlxn.dl.baijiakang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.activity.OrderDetailsActivity;
import com.zlxn.dl.baijiakang.activity.PjActivity;
import com.zlxn.dl.baijiakang.activity.ReleasePjActivity;
import com.zlxn.dl.baijiakang.base.BaseContentAdapter;
import com.zlxn.dl.baijiakang.base.BaseEmptyViewHolder;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.EmptyAdapter;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.CancelOrderBean;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.DateUtil;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.OnPasswordInputFinish;
import com.zlxn.dl.baijiakang.view.PopEnterPassword;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 10:02
 */
public class OrderAdapter extends EmptyAdapter<OrderListBean.DataBean> {

    private RecyclerView contentRv;
    private String type;
    private Activity activity;

    public OrderAdapter(Context context, int layoutResId, List<OrderListBean.DataBean> datas, String type, Activity activity) {
        super(context, layoutResId, datas);
        this.type = type;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseEmptyViewHolder viewHolder, OrderListBean.DataBean item) {
        viewHolder.getTextView(R.id.orderTime).setText(DateUtil.getDateToString(item.getOrder().getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
        viewHolder.getTextView(R.id.orderMoney).setText("实付款：¥ " + item.getOrder().getPrice() + "   券总额：¥ " + item.getOrder().getTicket());

        contentRv = (RecyclerView) viewHolder.getView(R.id.orderContentRv);
        contentRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        OrderContentAdapter adapter = new OrderContentAdapter(context, R.layout.item_content_order, item.getOrderDetail());
        contentRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                context.startActivity(new Intent(context, OrderDetailsActivity.class)
                        .putExtra("id", item.getOrder().getId() + "")
                        .putExtra("state", item.getOrder().getStatus() + "")
                        .putExtra("details", new Gson().toJson(item.getOrderDetail())));
            }
        });

        if (item.getOrder().getStatus() == 1) {//待支付
            viewHolder.getView(R.id.orderView).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.VISIBLE);
            viewHolder.getTextView(R.id.orderState).setText("待支付");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.VISIBLE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.VISIBLE);
            viewHolder.getButton(R.id.orderBtn1).setText("立即付款");
            viewHolder.getButton(R.id.orderBtn2).setText("取消订单");


            viewHolder.getButton(R.id.orderBtn1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new XPopup.Builder(context)
                            .enableDrag(true)
                            .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                            .asCustom(new PayPopupView(context, item))/*.enableDrag(false)*/
                            .show();

                }
            });

            viewHolder.getButton(R.id.orderBtn2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetrofitUtils.getApiUrl().cancelOrder(DataHelper.getIntergerSF(context, "userType") + "", item.getOrder().getId() + "", DataHelper.getStringSF(context, "token"))
                            .compose(RxHelper.observableIO2Main(context))
                            .subscribe(new MyObserver<CancelOrderBean>(context, true) {
                                @Override
                                public void onSuccess(CancelOrderBean result) {
                                  /*  DataHelper.setBooleanSF(context,"isCancel",true);
                                    DataHelper.setBooleanSF(context,"isAll",true);*/
                                    removeItem(item);
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(context, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else {
                                            ToastUtils.show(context, errorMsg);
                                        }
                                    }
                                }
                            });
                }
            });

        } else if (item.getOrder().getStatus() == 2) {//待发货
            viewHolder.getView(R.id.orderView).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.VISIBLE);
            viewHolder.getTextView(R.id.orderState).setText("待发货");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.VISIBLE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.GONE);
            viewHolder.getButton(R.id.orderBtn1).setText("等待发货");
        } else if (item.getOrder().getStatus() == 3) {//待收货
            viewHolder.getView(R.id.orderView).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.VISIBLE);
            viewHolder.getTextView(R.id.orderState).setText("待收货");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.VISIBLE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.GONE);
            viewHolder.getButton(R.id.orderBtn1).setText("确认收货");

            viewHolder.getButton(R.id.orderBtn1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetrofitUtils.getApiUrl().confirmTake(DataHelper.getIntergerSF(context, "userType") + "", item.getOrder().getId() + "", DataHelper.getStringSF(context, "token"))
                            .compose(RxHelper.observableIO2Main(context))
                            .subscribe(new MyObserver<String>(context, true) {
                                @Override
                                public void onSuccess(String result) {
                                    /*DataHelper.setBooleanSF(context,"isTake",true);
                                    DataHelper.setBooleanSF(context,"isAll",true);*/
                                    removeItem(item);
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(context, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else {
                                            ToastUtils.show(context, errorMsg);
                                        }
                                    }
                                }
                            });
                }
            });

        } else if (item.getOrder().getStatus() == 4) {//待评价
            viewHolder.getView(R.id.orderView).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.VISIBLE);
            viewHolder.getTextView(R.id.orderState).setText("待评价");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.VISIBLE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.GONE);
            viewHolder.getButton(R.id.orderBtn1).setText("评价");

            viewHolder.getButton(R.id.orderBtn1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, ReleasePjActivity.class)
                            .putExtra("id", item.getOrder().getId() + "")
                            .putExtra("location", "orderList")
                            .putExtra("details", new Gson().toJson(item.getOrderDetail())));
                }
            });

        } else if (item.getOrder().getStatus() == 5) {//已完成
            viewHolder.getView(R.id.orderView).setVisibility(View.GONE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.GONE);
            viewHolder.getTextView(R.id.orderState).setText("已完成");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.GONE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.GONE);
        }else if (item.getOrder().getStatus() == 7){
            viewHolder.getView(R.id.orderView).setVisibility(View.GONE);
            viewHolder.getView(R.id.orderLin).setVisibility(View.GONE);
            viewHolder.getTextView(R.id.orderState).setText("待打款");
            viewHolder.getButton(R.id.orderBtn1).setVisibility(View.GONE);
            viewHolder.getButton(R.id.orderBtn2).setVisibility(View.GONE);
        }
    }


    public class PayPopupView extends BottomPopupView {

        private OrderListBean.DataBean item;

        public PayPopupView(@NonNull Context context, OrderListBean.DataBean item) {
            super(context);
            this.item = item;
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

            if (DataHelper.getIntergerSF(context, "userType") == 1) {//代理商
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
                    RetrofitUtils.getApiUrl().payUrl(DataHelper.getIntergerSF(context, "userType") + "",
                            "", "4", item.getOrder().getId() + "",
                            DataHelper.getStringSF(context, "token"))
                            .compose(RxHelper.observableIO2Main(context))
                            .subscribe(new MyObserver<String>(context, true) {
                                @Override
                                public void onSuccess(String result) {
                                    item.getOrder().setStatus(7);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(context, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else {
                                            ToastUtils.show(context, errorMsg);
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
                    if (!DataHelper.getBooleanSF(context, "isPayPwd")) {
                        ToastUtils.show(context, "请设置支付密码");
                        return;
                    }
                    final PopEnterPassword popEnterPassword = new PopEnterPassword(activity, "支付金额", Double.parseDouble(item.getOrder().getPrice()));
                    popEnterPassword.getPwdView().setOnFinishInput(new OnPasswordInputFinish() {
                        @Override
                        public void inputFinish(String password) {
                            RetrofitUtils.getApiUrl().payUrl(DataHelper.getIntergerSF(context, "userType") + "",
                                    password, "3", item.getOrder().getId() + "",
                                    DataHelper.getStringSF(context, "token"))
                                    .compose(RxHelper.observableIO2Main(context))
                                    .subscribe(new MyObserver<String>(context, true) {
                                        @Override
                                        public void onSuccess(String result) {
                                            popEnterPassword.dismiss();
                                            if (type.equals("0")){
                                                item.getOrder().setStatus(2);
                                                notifyDataSetChanged();
                                            }else if (type.equals("1")){
                                                removeItem(item);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                            if (e != null) {
                                                ToastUtils.show(context, errorCode);
                                            } else {
                                                if (errorCode.equals("401")) {
                                                    Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                } else {
                                                    ToastUtils.show(context, errorMsg);
                                                }
                                            }
                                        }
                                    });
                        }
                    });

                    popEnterPassword.showAtLocation(contentRv,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

                }
            });

        }

    }

}
