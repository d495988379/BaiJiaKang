package com.zlxn.dl.baijiakang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.CommodityAdapter;
import com.zlxn.dl.baijiakang.adapter.SearchAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.base.EmptyAdapter;
import com.zlxn.dl.baijiakang.bean.CommodityListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.SpaceItemDecoration;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/19 9:47
 */
public class SearchActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.searchEdit)
    EditText searchEdit;
    @BindView(R.id.searchRv)
    RecyclerView searchRv;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.searchTv)
    TextView searchTv;
    private int page = 1;
    private MyObserver<CommodityListBean> myCommodityObserver;
    private SearchAdapter adapter;
    private String searchContent = "";

    @Override
    protected void initView() {
        setContentView(R.layout.act_search);
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

        swipeToLoadLayout.setTargetView(searchRv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);
        searchRv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        searchRv.addItemDecoration(new SpaceItemDecoration(2, ArmsUtils.dip2px(SearchActivity.this, 8), ArmsUtils.dip2px(SearchActivity.this, 10)));


        myCommodityObserver = new MyObserver<CommodityListBean>(SearchActivity.this, true) {
            @Override
            public void onSuccess(CommodityListBean result) {
                if (result.getRows() != null ) {

                    if (result.getRows().size() > 0){
                        searchRv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                    }else {
                        searchRv.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
                    }

                    adapter = new SearchAdapter(SearchActivity.this, R.layout.item_commodity, result.getRows());
                    searchRv.setAdapter(adapter);

                    adapter.setOnItemClickListener(new EmptyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(SearchActivity.this, DetailsActivity.class)
                                    .putExtra("id", result.getRows().get(position).getId() + ""));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(SearchActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(SearchActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().searchCommodity(DataHelper.getIntergerSF(SearchActivity.this, "userType") + "", searchContent, page + "", "10", DataHelper.getStringSF(SearchActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(SearchActivity.this))
                .subscribe(myCommodityObserver);

    }

    @Override
    protected void setListen() {
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    // hideKeyboard(searchEdit);
                    //do something
                    //doSearch();

                    if (textView.getText().toString().length() == 0) {
                        ToastUtils.show(SearchActivity.this, "请输入搜索商品");
                        return false;
                    }

                    searchContent = textView.getText().toString();

                    page = 1;

                    myCommodityObserver = new MyObserver<CommodityListBean>(SearchActivity.this, true) {
                        @Override
                        public void onSuccess(CommodityListBean result) {
                            if (result.getRows() != null) {

                                if (result.getRows().size() > 0){
                                    searchRv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                                }else {
                                    searchRv.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
                                }

                                adapter = new SearchAdapter(SearchActivity.this, R.layout.item_commodity, result.getRows());
                                searchRv.setAdapter(adapter);

                                adapter.setOnItemClickListener(new EmptyAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        startActivity(new Intent(SearchActivity.this, DetailsActivity.class)
                                                .putExtra("id", result.getRows().get(position).getId() + ""));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                            if (e != null) {
                                ToastUtils.show(SearchActivity.this, errorCode);
                            } else {
                                if (errorCode.equals("401")) {
                                    Intent intent = new Intent(SearchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    ToastUtils.show(SearchActivity.this, errorMsg);
                                }
                            }
                        }
                    };

                    RetrofitUtils.getApiUrl().searchCommodity(DataHelper.getIntergerSF(SearchActivity.this, "userType") + "", searchContent, page + "", "10", DataHelper.getStringSF(SearchActivity.this, "token"))
                            .compose(RxHelper.observableIO2Main(SearchActivity.this))
                            .subscribe(myCommodityObserver);


                    return false;
                }
                return false;
            }
        });

        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEdit.getText().toString().length() == 0) {
                    ToastUtils.show(SearchActivity.this, "请输入搜索商品");
                    return;
                }

                searchContent = searchEdit.getText().toString();

                page = 1;
                myCommodityObserver = new MyObserver<CommodityListBean>(SearchActivity.this, true) {
                    @Override
                    public void onSuccess(CommodityListBean result) {
                        if (result.getRows() != null) {

                            if (result.getRows().size() > 0){
                                searchRv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                            }else {
                                searchRv.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
                            }

                            adapter = new SearchAdapter(SearchActivity.this, R.layout.item_commodity, result.getRows());
                            searchRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new EmptyAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivity(new Intent(SearchActivity.this, DetailsActivity.class)
                                            .putExtra("id", result.getRows().get(position).getId() + ""));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(SearchActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(SearchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(SearchActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().searchCommodity(DataHelper.getIntergerSF(SearchActivity.this, "userType") + "", searchContent, page + "", "10", DataHelper.getStringSF(SearchActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(SearchActivity.this))
                        .subscribe(myCommodityObserver);
            }
        });
    }


    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onLoadMore() {
        page = page + 1;
        myCommodityObserver = new MyObserver<CommodityListBean>(SearchActivity.this, false) {
            @Override
            public void onSuccess(CommodityListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getRows() != null && result.getRows().size() > 0) {
                    loadRecyclerView(result.getRows());
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
                if (e != null) {
                    ToastUtils.show(SearchActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(SearchActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().searchCommodity(DataHelper.getIntergerSF(SearchActivity.this, "userType") + "", searchContent, page + "", "10", DataHelper.getStringSF(SearchActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(SearchActivity.this))
                .subscribe(myCommodityObserver);

    }

    private void loadRecyclerView(List<CommodityListBean.RowsBean> rows) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(rows);
    }

    @Override
    public void onRefresh() {
        page = 1;
        myCommodityObserver = new MyObserver<CommodityListBean>(SearchActivity.this, false) {
            @Override
            public void onSuccess(CommodityListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getRows() != null) {

                    if (result.getRows().size() > 0){
                        searchRv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                    }else {
                        searchRv.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
                    }

                    adapter = new SearchAdapter(SearchActivity.this, R.layout.item_commodity, result.getRows());
                    searchRv.setAdapter(adapter);

                    adapter.setOnItemClickListener(new EmptyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(SearchActivity.this, DetailsActivity.class)
                                    .putExtra("id", result.getRows().get(position).getId() + ""));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setRefreshing(false);
                if (e != null) {
                    ToastUtils.show(SearchActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(SearchActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().searchCommodity(DataHelper.getIntergerSF(SearchActivity.this, "userType") + "", searchContent, page + "", "10", DataHelper.getStringSF(SearchActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(SearchActivity.this))
                .subscribe(myCommodityObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myCommodityObserver != null){
            myCommodityObserver.cancleRequest();
        }
    }
}
