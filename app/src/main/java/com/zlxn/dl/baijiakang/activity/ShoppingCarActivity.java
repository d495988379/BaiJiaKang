package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.ShopAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.SlideRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/19 15:53
 */
public class ShoppingCarActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.shoppingRv)
    SwipeRecyclerView shoppingRv;
    @BindView(R.id.iv_select_all)
    ImageView ivSelectAll;
    @BindView(R.id.ll_select_all)
    LinearLayout llSelectAll;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_total_quan)
    TextView tvTotalQuan;
    @BindView(R.id.rl_total_price)
    LinearLayout rlTotalPrice;
    @BindView(R.id.rl)
    RelativeLayout rl;

    private MyObserver<ShopCarBean> myObserver;
    private ShopAdapter adapter;
    private List<ShopCarBean.DataBean> data;
    private List<ShopCarBean.DataBean> spendData;
    private boolean isSelect = false;
    private double money = 0.0;
    private int quan = 0;
    private int count;
    private List<String> ids;
    private BigDecimal bg;

    @Override
    protected void initView() {
        setContentView(R.layout.act_shopping);
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

        initSlide();

        myObserver = new MyObserver<ShopCarBean>(this, true) {
            @Override
            public void onSuccess(ShopCarBean result) {
                if (result.getData() != null && result.getData().size() > 0) {

                    data = result.getData();

                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }

                    adapter = new ShopAdapter(ShoppingCarActivity.this, R.layout.item_shop, data);
                    shoppingRv.setAdapter(adapter);

                    //购物车减
                    adapter.setOnJianClickListener(new ShopAdapter.OnJianClickListener() {
                        @Override
                        public void onJianClick(View view, int position) {
                            count = data.get(position).getNumber();
                            if (count > 1) {
                                count--;
                                RetrofitUtils.getApiUrl().jianComm(DataHelper.getIntergerSF(ShoppingCarActivity.this, "userType") + "",data.get(position).getId() + "", DataHelper.getStringSF(ShoppingCarActivity.this, "token"))
                                        .compose(RxHelper.observableIO2Main(ShoppingCarActivity.this))
                                        .subscribe(new MyObserver<String>(ShoppingCarActivity.this, true) {
                                            @Override
                                            public void onSuccess(String result) {
                                                data.get(position).setNumber(count);
                                                adapter.notifyDataSetChanged();

                                                money = 0.0;
                                                quan = 0;
                                                for (int i = 0; i < data.size(); i++) {
                                                    if (data.get(i).isSelect()) {
                                                        double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                                                        int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                                                        money = money + dMoney;
                                                        quan = quan + dQuan;
                                                    }
                                                }

                                                bg = new BigDecimal(money);
                                                double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                                                tvTotalPrice.setText("实付款：¥" + flMoney);
                                                tvTotalQuan.setText("券总额：¥" + quan);

                                            }

                                            @Override
                                            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                                if (e != null) {
                                                    ToastUtils.show(ShoppingCarActivity.this, errorCode);
                                                } else {
                                                    if (errorCode.equals("401")) {
                                                        Intent intent = new Intent(ShoppingCarActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        ToastUtils.show(ShoppingCarActivity.this, errorMsg);
                                                    }
                                                }
                                            }
                                        });
                            }

                        }
                    });

                    //购物车加
                    adapter.setOnAddClickListener(new ShopAdapter.OnAddClickListener() {
                        @Override
                        public void onAddClick(View view, int position) {
                            count = data.get(position).getNumber();
                            count++;

                            RetrofitUtils.getApiUrl().addComm(DataHelper.getIntergerSF(ShoppingCarActivity.this, "userType") + "",data.get(position).getId() + "", DataHelper.getStringSF(ShoppingCarActivity.this, "token"))
                                    .compose(RxHelper.observableIO2Main(ShoppingCarActivity.this))
                                    .subscribe(new MyObserver<String>(ShoppingCarActivity.this, true) {
                                        @Override
                                        public void onSuccess(String result) {
                                            data.get(position).setNumber(count);
                                            adapter.notifyDataSetChanged();

                                            money = 0.0;
                                            quan = 0;
                                            for (int i = 0; i < data.size(); i++) {
                                                if (data.get(i).isSelect()) {
                                                    double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                                                    int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                                                    money = money + dMoney;
                                                    quan = quan + dQuan;
                                                }
                                            }

                                            bg = new BigDecimal(money);
                                            double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                            tvTotalPrice.setText("实付款：¥" + flMoney);
                                            tvTotalQuan.setText("券总额：¥" + quan);

                                        }

                                        @Override
                                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                            if (e != null) {
                                                ToastUtils.show(ShoppingCarActivity.this, errorCode);
                                            } else {
                                                if (errorCode.equals("401")) {
                                                    Intent intent = new Intent(ShoppingCarActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    ToastUtils.show(ShoppingCarActivity.this, errorMsg);
                                                }
                                            }
                                        }
                                    });

                        }
                    });

                    //购物车单选/反选
                    adapter.setOnSelectClickListener(new ShopAdapter.OnSelectClickListener() {
                        @Override
                        public void onSelectClick(View view, int position) {
                            money = 0.0;
                            quan = 0;
                            if (data.get(position).isSelect()) {
                                data.get(position).setSelect(false);
                            } else {
                                data.get(position).setSelect(true);
                            }


                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).isSelect()) {
                                    double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                                    int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                                    money = money + dMoney;
                                    quan = quan + dQuan;
                                }
                            }


                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).isSelect()) {
                                    isSelect = true;
                                } else {
                                    isSelect = false;
                                    break;
                                }
                            }

                            if (isSelect) {
                                ivSelectAll.setImageResource(R.mipmap.ic_select_img);
                            } else {
                                ivSelectAll.setImageResource(R.mipmap.ic_unselect_img);
                            }

                            bg = new BigDecimal(money);
                            double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                            tvTotalPrice.setText("实付款：¥" + flMoney);
                            tvTotalQuan.setText("券总额：¥" + quan);

                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(ShoppingCarActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(ShoppingCarActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(ShoppingCarActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().carList(DataHelper.getIntergerSF(ShoppingCarActivity.this, "userType") + "",DataHelper.getStringSF(ShoppingCarActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(ShoppingCarActivity.this))
                .subscribe(myObserver);

    }

    private void initSlide() {
        shoppingRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        shoppingRv.addItemDecoration(divider);


        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(ShoppingCarActivity.this);
                deleteItem.setText("删除")
                        .setBackgroundColor(getResources().getColor(R.color.red))
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(14) // 文字大小。
                        .setWidth(150)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                rightMenu.addMenuItem(deleteItem);
            }
        };
        shoppingRv.setSwipeMenuCreator(mSwipeMenuCreator);


        OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                RetrofitUtils.getApiUrl().deleteCar(DataHelper.getIntergerSF(ShoppingCarActivity.this, "userType") + "",data.get(adapterPosition).getId() + "", DataHelper.getStringSF(ShoppingCarActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(ShoppingCarActivity.this))
                        .subscribe(new MyObserver<String>(ShoppingCarActivity.this, true) {
                            @Override
                            public void onSuccess(String result) {
                                menuBridge.closeMenu();
                                data.remove(adapterPosition);
                                adapter.notifyDataSetChanged();

                                money = 0.0;
                                quan = 0;

                                if (data != null && data.size() > 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        if (data.get(i).isSelect()) {
                                            double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                                            int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                                            money = money + dMoney;
                                            quan = quan + dQuan;
                                        }
                                    }

                                    bg = new BigDecimal(money);
                                    double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    tvTotalPrice.setText("实付款：¥" + flMoney);
                                    tvTotalQuan.setText("券总额：¥" + quan);

                                } else {
                                    tvTotalPrice.setText("实付款：¥" + money);
                                    tvTotalQuan.setText("券总额：¥" + quan);
                                }

                            }

                            @Override
                            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                if (e != null) {
                                    ToastUtils.show(ShoppingCarActivity.this, errorCode);
                                } else {
                                    if (errorCode.equals("401")) {
                                        Intent intent = new Intent(ShoppingCarActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        ToastUtils.show(ShoppingCarActivity.this, errorMsg);
                                    }
                                }
                            }
                        });

            }
        };
        // 菜单点击监听。
        shoppingRv.setOnItemMenuClickListener(mMenuItemClickListener);


    }

    @Override
    protected void setListen() {
        commonBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit = commonBar.getRightTextView().getText().toString().trim();
                shoppingRv.smoothCloseMenu();
                if (edit.equals("管理")) {
                    commonBar.getRightTextView().setText("完成");
                    rlTotalPrice.setVisibility(View.GONE);
                    btnOrder.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);
                } else {
                    commonBar.getRightTextView().setText("管理");
                    rlTotalPrice.setVisibility(View.VISIBLE);
                    btnOrder.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                }
            }
        });


        //全选
        ivSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelect) {//选中
                    ivSelectAll.setImageResource(R.mipmap.ic_select_img);
                    isSelect = true;
                    if (data != null && data.size() > 0) {

                        money = 0.0;
                        quan = 0;

                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(true);
                            double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                            int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                            money = money + dMoney;
                            quan = quan + dQuan;
                        }

                        bg = new BigDecimal(money);
                        double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                        tvTotalPrice.setText("实付款：¥" + flMoney);
                        tvTotalQuan.setText("券总额：¥" + quan);

                        adapter.notifyDataSetChanged();
                    }


                } else {//未选中
                    ivSelectAll.setImageResource(R.mipmap.ic_unselect_img);
                    isSelect = false;

                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(false);
                        }
                        money = 0.0;
                        quan = 0;

                        tvTotalPrice.setText("实付款：¥" + money);
                        tvTotalQuan.setText("券总额：¥" + quan);

                        adapter.notifyDataSetChanged();
                    }

                }
            }
        });

        //选中删除
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data != null && data.size() > 0) {
                    ids = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).isSelect()) {
                            ids.add(data.get(i).getId() + "");
                        }
                    }

                    if (ids != null && ids.size() > 0) {
                        String idStr = ArmsUtils.listToString(ids, ",");

                        RetrofitUtils.getApiUrl().deleteCar(DataHelper.getIntergerSF(ShoppingCarActivity.this, "userType") + "",idStr, DataHelper.getStringSF(ShoppingCarActivity.this, "token"))
                                .compose(RxHelper.observableIO2Main(ShoppingCarActivity.this))
                                .subscribe(new MyObserver<String>(ShoppingCarActivity.this, true) {
                                    @Override
                                    public void onSuccess(String result) {

                                        money = 0.0;
                                        quan = 0;

                                        for (int i = 0; i < data.size(); i++) {
                                            for (int i1 = 0; i1 < ids.size(); i1++) {
                                                if (data.get(i).getId() == Integer.parseInt(ids.get(i1))) {
                                                    data.remove(i);
                                                } else {
                                                    if (data.get(i).isSelect()) {
                                                        double dMoney = Double.parseDouble(data.get(i).getPrice()) * data.get(i).getNumber();
                                                        int dQuan = data.get(i).getTicket() * data.get(i).getNumber();
                                                        money = money + dMoney;
                                                        quan = quan + dQuan;
                                                    }
                                                }
                                            }
                                        }

                                        if (data != null && data.size() > 0) {
                                            for (int i = 0; i < data.size(); i++) {
                                                if (data.get(i).isSelect()) {
                                                    isSelect = true;
                                                } else {
                                                    isSelect = false;
                                                    break;
                                                }
                                            }
                                            if (isSelect) {
                                                ivSelectAll.setImageResource(R.mipmap.ic_select_img);
                                            } else {
                                                ivSelectAll.setImageResource(R.mipmap.ic_unselect_img);
                                            }
                                        } else {
                                            isSelect = false;
                                            ivSelectAll.setImageResource(R.mipmap.ic_unselect_img);
                                        }

                                        bg = new BigDecimal(money);
                                        double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                        tvTotalPrice.setText("实付款：¥" + flMoney);
                                        tvTotalQuan.setText("券总额：¥" + quan);
                                        adapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                        if (e != null) {
                                            ToastUtils.show(ShoppingCarActivity.this, errorCode);
                                        } else {
                                            if (errorCode.equals("401")) {
                                                Intent intent = new Intent(ShoppingCarActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            } else {
                                                ToastUtils.show(ShoppingCarActivity.this, errorMsg);
                                            }
                                        }
                                    }
                                });
                    }

                }
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spendData = new ArrayList<>();
                if (data != null && data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).isSelect()) {
                            spendData.add(data.get(i));
                        }
                    }

                    bg = new BigDecimal(money);
                    double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    if (spendData != null && spendData.size() > 0){
                        startActivity(new Intent(ShoppingCarActivity.this, ConfirmOrderActivity.class)
                                .putExtra("data",new Gson().toJson(spendData))
                        .putExtra("money",flMoney + "")
                        .putExtra("quan",quan + "")
                        .putExtra("type","1"));
                    }


                }

            }
        });
    }
}
