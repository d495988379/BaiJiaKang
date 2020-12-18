package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.JyOrderAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.JyOrderListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/15 10:45
 */
public class JyOrderActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.jyRv)
    RecyclerView jyRv;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int page = 1;
    private MyObserver<JyOrderListBean> myObserver;
    private JyOrderAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.act_jy_order);
    }

    @Override
    protected void initData() {
        commonBar.setBackgroundResource(R.color.main_color);

        jyRv.setLayoutManager(new LinearLayoutManager(this));

        swipeToLoadLayout.setTargetView(jyRv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);



        myObserver = new MyObserver<JyOrderListBean>(JyOrderActivity.this, true) {
            @Override
            public void onSuccess(JyOrderListBean result) {
                if (result.getData() != null) {
                    adapter = new JyOrderAdapter(JyOrderActivity.this, R.layout.item_yh_order, result.getData());
                    jyRv.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(JyOrderActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(JyOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(JyOrderActivity.this, errorMsg);
                    }
                }
            }
        };
        RetrofitUtils.getApiUrl().userJyOrder(DataHelper.getStringSF(JyOrderActivity.this, "userPhone"), page + "", "10",
                DataHelper.getStringSF(JyOrderActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(JyOrderActivity.this))
                .subscribe(myObserver);


    }

    @Override
    protected void setListen() {
        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onLoadMore() {
        page = page + 1;

        myObserver = new MyObserver<JyOrderListBean>(JyOrderActivity.this, false) {
            @Override
            public void onSuccess(JyOrderListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getData() != null) {
                    loadRecyclerView(result.getData());
                }

            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
                if (e != null) {
                    ToastUtils.show(JyOrderActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(JyOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(JyOrderActivity.this, errorMsg);
                    }
                }
            }
        };
        RetrofitUtils.getApiUrl().userJyOrder(DataHelper.getStringSF(JyOrderActivity.this, "userPhone"), page + "", "10",
                DataHelper.getStringSF(JyOrderActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(JyOrderActivity.this))
                .subscribe(myObserver);

    }

    private void loadRecyclerView(List<JyOrderListBean.DataBean> data) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(data);
    }

    @Override
    public void onRefresh() {
        page = 1;

        myObserver = new MyObserver<JyOrderListBean>(JyOrderActivity.this, false) {
            @Override
            public void onSuccess(JyOrderListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getData() != null) {
                    adapter = new JyOrderAdapter(JyOrderActivity.this, R.layout.item_yh_order, result.getData());
                    jyRv.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setRefreshing(false);
                if (e != null) {
                    ToastUtils.show(JyOrderActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(JyOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(JyOrderActivity.this, errorMsg);
                    }
                }
            }
        };
        RetrofitUtils.getApiUrl().userJyOrder(DataHelper.getStringSF(JyOrderActivity.this, "userPhone"), page + "", "10",
                DataHelper.getStringSF(JyOrderActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(JyOrderActivity.this))
                .subscribe(myObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null){
            myObserver.cancleRequest();
        }
    }
}
