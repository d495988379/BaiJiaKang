package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.PjAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.PjListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.MyScrollView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/23 11:47
 */
public class PjActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.pjRv)
    RecyclerView pjRv;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int page = 1;
    private MyObserver<PjListBean> myObserver;
    private String id;
    private PjAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.act_pj);
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

        id = getIntent().getStringExtra("id");

        pjRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        swipeToLoadLayout.setTargetView(pjRv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);

        myObserver = new MyObserver<PjListBean>(PjActivity.this, true) {
            @Override
            public void onSuccess(PjListBean result) {
                if (result.getData() != null) {
                    //emptyLin.setVisibility(View.GONE);
                    pjRv.setVisibility(View.VISIBLE);
                    adapter = new PjAdapter(PjActivity.this, R.layout.item_pj, result.getData());
                    pjRv.setAdapter(adapter);
                /*} else {
                    emptyLin.setVisibility(View.VISIBLE);
                    pjRv.setVisibility(View.GONE);
                }*/
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(PjActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(PjActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(PjActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().pjList(DataHelper.getIntergerSF(PjActivity.this, "userType") + "", page + "", "10", id, DataHelper.getStringSF(PjActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(PjActivity.this))
                .subscribe(myObserver);

    }

    @Override
    public void onLoadMore() {
        page = page + 1;

        myObserver = new MyObserver<PjListBean>(PjActivity.this, false) {
            @Override
            public void onSuccess(PjListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getData() != null && result.getData().size() > 0) {
                    loadRecyclerView(result.getData());
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
                if (e != null) {
                    ToastUtils.show(PjActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(PjActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(PjActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().pjList(DataHelper.getIntergerSF(PjActivity.this, "userType") + "", page + "", "10", id, DataHelper.getStringSF(PjActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(PjActivity.this))
                .subscribe(myObserver);

    }

    private void loadRecyclerView(List<PjListBean.DataBean> data) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(data);
    }

    @Override
    public void onRefresh() {
        page = 1;
        myObserver = new MyObserver<PjListBean>(PjActivity.this, false) {
            @Override
            public void onSuccess(PjListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getData() != null) {
                  //  emptyLin.setVisibility(View.GONE);
                    pjRv.setVisibility(View.VISIBLE);
                    adapter = new PjAdapter(PjActivity.this, R.layout.item_pj, result.getData());
                    pjRv.setAdapter(adapter);
               /* } else {
                    emptyLin.setVisibility(View.VISIBLE);
                    pjRv.setVisibility(View.GONE);*/
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setRefreshing(false);
                if (e != null) {
                    ToastUtils.show(PjActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(PjActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(PjActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().pjList(DataHelper.getIntergerSF(PjActivity.this, "userType") + "", page + "", "10", id, DataHelper.getStringSF(PjActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(PjActivity.this))
                .subscribe(myObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myObserver.cancleRequest();
    }
}
