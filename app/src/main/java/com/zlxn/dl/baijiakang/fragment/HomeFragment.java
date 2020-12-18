package com.zlxn.dl.baijiakang.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.stx.xhb.androidx.XBanner;
import com.stx.xmarqueeview.XMarqueeView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.BannerWebActivity;
import com.zlxn.dl.baijiakang.activity.DetailsActivity;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.activity.SearchActivity;
import com.zlxn.dl.baijiakang.activity.SecondActivity;
import com.zlxn.dl.baijiakang.adapter.CommodityAdapter;
import com.zlxn.dl.baijiakang.adapter.FlAdapter;
import com.zlxn.dl.baijiakang.adapter.MarqueeViewAdapter;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.base.BaseFragment;
import com.zlxn.dl.baijiakang.bean.BannerListBean;
import com.zlxn.dl.baijiakang.bean.CommodityListBean;
import com.zlxn.dl.baijiakang.bean.CustomViewsInfo;
import com.zlxn.dl.baijiakang.bean.OneClassBean;
import com.zlxn.dl.baijiakang.http.Constans;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.MyScrollView;
import com.zlxn.dl.baijiakang.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/13 17:50
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.homeStateBar)
    View homeStateBar;
    @BindView(R.id.homeFlRv)
    RecyclerView homeFlRv;
    @BindView(R.id.upView)
    XMarqueeView marqueeView;
    @BindView(R.id.homeRv)
    RecyclerView homeRv;
    @BindView(R.id.homeBanner)
    XBanner homeBanner;
    @BindView(R.id.jxTv)
    TextView jxTv;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.myScrollView)
    MyScrollView myScrollView;
    @BindView(R.id.searchLin)
    LinearLayout searchLin;
    @BindView(R.id.homeJyTv)
    TextView homeJyTv;
    @BindView(R.id.homeHyqyTv)
    TextView homeHyqyTv;
    private List<String> pmdData = new ArrayList<>();
    private MyObserver<List<BannerListBean>> myObserver;
    private MyObserver<List<OneClassBean>> myClassObserver;
    private MyObserver<CommodityListBean> myCommodityObserver;
    private List<CustomViewsInfo> bannerImg;
    private CommodityAdapter adapter1;
    private FlAdapter adapter;
    private int page = 1;
    private FragmentListener mFragmentListener;


    public interface FragmentListener {
        void onFragment(String content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentListener) {
            mFragmentListener = (FragmentListener) context;
        } else {
            throw new IllegalArgumentException("Activity must implements FragmentListener");
        }

    }


    @Override
    protected int getContentViewLayout() {
        return R.layout.frg_home;
    }

    @Override
    protected void initView() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homeStateBar.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        homeStateBar.setLayoutParams(lp);


        swipeToLoadLayout.setTargetView(myScrollView);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);


        marqueeView.setAdapter(new MarqueeViewAdapter(pmdData, getActivity()));

        homeFlRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeRv.addItemDecoration(new SpaceItemDecoration(2, ArmsUtils.dip2px(getActivity(), 8), ArmsUtils.dip2px(getActivity(), 10)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            homeBanner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                }
            });
            homeBanner.setClipToOutline(true);
        }

        myObserver = new MyObserver<List<BannerListBean>>(getActivity(), true) {
            @Override
            public void onSuccess(List<BannerListBean> result) {
                if (result != null && result.size() > 0) {
                    bannerImg = new ArrayList<>();
                    for (int i = 0; i < result.size(); i++) {
                        bannerImg.add(new CustomViewsInfo(Constans.PicUrl + result.get(i).getPic()));
                    }

                    homeBanner.setBannerData(bannerImg);

                    homeBanner.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {

                            //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                            //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                            Glide.with(getActivity()).load(bannerImg.get(position).getXBannerUrl()).into((ImageView) view);
                        }
                    });

                    homeBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                        @Override
                        public void onItemClick(XBanner banner, Object model, View view, int position) {
                            startActivity(new Intent(getActivity(), BannerWebActivity.class)
                                    .putExtra("url", result.get(position).getLink() != null ? result.get(position).getLink() : ""));
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


        RetrofitUtils.getApiUrl().bannerList(DataHelper.getIntergerSF(getActivity(), "userType") + "", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);


        myClassObserver = new MyObserver<List<OneClassBean>>(getActivity(), true) {
            @Override
            public void onSuccess(List<OneClassBean> result) {
                if (result != null) {
                    adapter = new FlAdapter(getActivity(), R.layout.item_fl, result);
                    homeFlRv.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    result.get(i).setSelect(false);
                                }

                                result.get(position).setSelect(true);
                                startActivity(new Intent(getActivity(), SecondActivity.class)
                                        .putExtra("id", result.get(position).getId() + "")
                                        .putExtra("img", result.get(position).getPic() != null ? result.get(position).getPic() : "")
                                        .putExtra("data", new Gson().toJson(result)));
                            }

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


        RetrofitUtils.getApiUrl().oneClass(DataHelper.getIntergerSF(getActivity(), "userType") + "", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myClassObserver);


        myCommodityObserver = new MyObserver<CommodityListBean>(getActivity(), true) {
            @Override
            public void onSuccess(CommodityListBean result) {
                if (result.getRows() != null && result.getRows().size() > 0) {
                    adapter1 = new CommodityAdapter(getActivity(), R.layout.item_commodity, result.getRows());
                    homeRv.setAdapter(adapter1);

                    adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getActivity(), DetailsActivity.class)
                                    .putExtra("id", result.getRows().get(position).getId() + ""));
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

        RetrofitUtils.getApiUrl().commodityList(DataHelper.getIntergerSF(getActivity(), "userType") + "", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myCommodityObserver);


        jxTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   page = 1;

                myCommodityObserver = new MyObserver<List<CommodityListBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(List<CommodityListBean> result) {
                        if (result != null && result.size() > 0) {
                            adapter1 = new CommodityAdapter(getActivity(), R.layout.item_commodity, result);
                            homeRv.setAdapter(adapter1);

                            adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivity(new Intent(getActivity(), DetailsActivity.class)
                                            .putExtra("id", result.get(position).getId() + ""));
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

                RetrofitUtils.getApiUrl().commodityList(DataHelper.getStringSF(getActivity(), "token"))
                        .compose(RxHelper.observableIO2Main(getActivity()))
                        .subscribe(myCommodityObserver);*/
            }
        });

        searchLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        homeJyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.onFragment("加油");
            }
        });

        homeHyqyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.onFragment("会员权益");
            }
        });
    }

    @Override
    protected void initData() {

        pmdData.add("周莘羽刚刚购买云南白药1");
        pmdData.add("周莘羽刚刚购买云南白药2");
        pmdData.add("周莘羽刚刚购买云南白药3");
        pmdData.add("周莘羽刚刚购买云南白药4");
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
        if (myCommodityObserver != null) {
            myCommodityObserver.cancleRequest();
        }
    }

    @Override
    public void onLoadMore() {
        page = page + 1;

        myCommodityObserver = new MyObserver<CommodityListBean>(getActivity(), false) {
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

        RetrofitUtils.getApiUrl().commodityList(DataHelper.getIntergerSF(getActivity(), "userType") + "", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myCommodityObserver);

    }


    private void loadRecyclerView(List<CommodityListBean.RowsBean> data) {
        int position = adapter1.getItemCount() - 1;
        adapter1.loadMoreData(data);
    }

    @Override
    public void onRefresh() {
        page = 1;

        myCommodityObserver = new MyObserver<CommodityListBean>(getActivity(), false) {
            @Override
            public void onSuccess(CommodityListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getRows() != null) {
                    adapter1 = new CommodityAdapter(getActivity(), R.layout.item_commodity, result.getRows());
                    homeRv.setAdapter(adapter1);

                    adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getActivity(), DetailsActivity.class)
                                    .putExtra("id", result.getRows().get(position).getId() + ""));
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

        RetrofitUtils.getApiUrl().commodityList(DataHelper.getIntergerSF(getActivity(), "userType") + "", page + "", "10", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myCommodityObserver);

    }
}
