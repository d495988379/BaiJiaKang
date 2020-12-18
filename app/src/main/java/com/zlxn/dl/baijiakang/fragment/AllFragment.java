package com.zlxn.dl.baijiakang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.AddressListActivity;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.activity.OrderDetailsActivity;
import com.zlxn.dl.baijiakang.activity.ShoppingCarActivity;
import com.zlxn.dl.baijiakang.adapter.OrderAdapter;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.base.BaseEmptyAdapter;
import com.zlxn.dl.baijiakang.base.VpFragment;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/13 17:09
 */
public class AllFragment extends VpFragment implements OnRefreshListener, OnLoadMoreListener {

    private int page = 1;
    private RecyclerView rv;
    private SwipeToLoadLayout swipeToLoadLayout;
    private OrderAdapter adapter;
    private MyObserver<OrderListBean> myObserver;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_order, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);

        swipeToLoadLayout.setTargetView(rv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {//显示界面
            page = 1;
            myObserver = new MyObserver<OrderListBean>(getActivity(), true) {
                @Override
                public void onSuccess(OrderListBean result) {
                    if (result.getData() != null) {
                        adapter = new OrderAdapter(getActivity(), R.layout.item_order, result.getData(),"0",getActivity());
                        rv.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseEmptyAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(getActivity(), OrderDetailsActivity.class)
                                        .putExtra("id", result.getData().get(position).getOrder().getId() + "")
                                        .putExtra("state", result.getData().get(position).getOrder().getStatus() + "")
                                        .putExtra("details", new Gson().toJson(result.getData().get(position).getOrderDetail())));
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    if (e != null) {
                        ToastUtils.show(getActivity(), errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(getActivity(), errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().orderList(DataHelper.getIntergerSF(getActivity(), "userType") + "","0", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                    .compose(RxHelper.observableIO2Main(getActivity()))
                    .subscribe(myObserver);

        } else {//隐藏界面
            if (myObserver != null) {
                myObserver.cancleRequest();
            }
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
    }

    @Override
    public void onLoadMore() {
        page = page + 1;

        myObserver = new MyObserver<OrderListBean>(getActivity(), false) {
            @Override
            public void onSuccess(OrderListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getData() != null && result.getData().size() > 0) {
                    loadRecyclerView(result.getData());
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
                if (e != null) {
                    ToastUtils.show(getActivity(), errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(getActivity(), errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().orderList(DataHelper.getIntergerSF(getActivity(), "userType") + "","0", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);

    }

    private void loadRecyclerView(List<OrderListBean.DataBean> data) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DataHelper.getBooleanSF(getActivity(), "isSet")) {
            DataHelper.setBooleanSF(getActivity(), "isSet", false);
            page = 1;
            myObserver = new MyObserver<OrderListBean>(getActivity(), true) {
                @Override
                public void onSuccess(OrderListBean result) {
                    if (result.getData() != null) {
                        adapter = new OrderAdapter(getActivity(), R.layout.item_order, result.getData(),"0",getActivity());
                        rv.setAdapter(adapter);

                        adapter.setOnItemClickListener(new BaseEmptyAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(getActivity(), OrderDetailsActivity.class)
                                        .putExtra("id", result.getData().get(position).getOrder().getId() + "")
                                        .putExtra("state", result.getData().get(position).getOrder().getStatus() + "")
                                        .putExtra("details", new Gson().toJson(result.getData().get(position).getOrderDetail())));
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    if (e != null) {
                        ToastUtils.show(getActivity(), errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(getActivity(), errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().orderList(DataHelper.getIntergerSF(getActivity(), "userType") + "","0", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                    .compose(RxHelper.observableIO2Main(getActivity()))
                    .subscribe(myObserver);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        myObserver = new MyObserver<OrderListBean>(getActivity(), false) {
            @Override
            public void onSuccess(OrderListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getData() != null) {
                    adapter = new OrderAdapter(getActivity(), R.layout.item_order, result.getData(),"0",getActivity());
                    rv.setAdapter(adapter);

                    adapter.setOnItemClickListener(new BaseEmptyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getActivity(), OrderDetailsActivity.class)
                                    .putExtra("id", result.getData().get(position).getOrder().getId() + "")
                                    .putExtra("state", result.getData().get(position).getOrder().getStatus() + "")
                                    .putExtra("details", new Gson().toJson(result.getData().get(position).getOrderDetail())));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setRefreshing(false);
                if (e != null) {
                    ToastUtils.show(getActivity(), errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(getActivity(), errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().orderList(DataHelper.getIntergerSF(getActivity(), "userType") + "","0", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }
}
