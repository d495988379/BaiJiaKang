package com.zlxn.dl.baijiakang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.stx.xhb.androidx.XBanner;
import com.stx.xmarqueeview.XMarqueeView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.CommodityAdapter;
import com.zlxn.dl.baijiakang.adapter.HomeTopicPagerAdapter;
import com.zlxn.dl.baijiakang.adapter.MarqueeViewAdapter;
import com.zlxn.dl.baijiakang.adapter.SecondFlAdapter;
import com.zlxn.dl.baijiakang.adapter.TopicAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.bean.ChildBean;
import com.zlxn.dl.baijiakang.bean.CommodityListBean;
import com.zlxn.dl.baijiakang.bean.CustomViewsInfo;
import com.zlxn.dl.baijiakang.bean.OneClassBean;
import com.zlxn.dl.baijiakang.http.Constans;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.DimensionConvert;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.MyScrollView;
import com.zlxn.dl.baijiakang.view.PageRecyclerView;
import com.zlxn.dl.baijiakang.view.SpaceItemDecoration;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/21 16:24
 */
public class SecondActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener,TopicAdapter.OnItemClickListener{

    @BindView(R.id.homeStateBar)
    View homeStateBar;
    @BindView(R.id.upView)
    XMarqueeView marqueeView;
    /* @BindView(R.id.secondRv)
     PageRecyclerView secondRv;*/
    @BindView(R.id.homeRv)
    RecyclerView homeRv;
    @BindView(R.id.homeBanner)
    XBanner homeBanner;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myScrollView)
    MyScrollView myScrollView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.searchLin)
    LinearLayout searchLin;
    /*@BindView(R.id.indicator)
    PageIndicatorView indicator;*/
    @BindView(R.id.jxTv)
    TextView jxTv;
    @BindView(R.id.homeFlRv)
    RecyclerView homeFlRv;
    @BindView(R.id.topicViewPager)
    ViewPager topicViewPager;
    @BindView(R.id.topicIndicator)
    MagicIndicator topicIndicator;
    private List<String> pmdData = new ArrayList<>();
    private MyObserver<List<ChildBean>> myChildObserver;
    private List<CustomViewsInfo> bannerImg;
    private MyObserver<CommodityListBean> myCommodityObserver;
    private CommodityAdapter adapter1;
    private int page = 1;
    private String id;
    private PageRecyclerView.PageAdapter myAdapter = null;
    private SecondFlAdapter adapter2;

    @Override
    protected void initView() {
        setContentView(R.layout.act_second);
    }

    @Override
    protected void initData() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homeStateBar.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        homeStateBar.setLayoutParams(lp);


        swipeToLoadLayout.setTargetView(myScrollView);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(SecondActivity.this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(SecondActivity.this).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);



        pmdData.add("周莘羽刚刚购买云南白药1");
        pmdData.add("周莘羽刚刚购买云南白药2");
        pmdData.add("周莘羽刚刚购买云南白药3");
        pmdData.add("周莘羽刚刚购买云南白药4");


        bannerImg = new ArrayList<>();
        bannerImg.add(new CustomViewsInfo(Constans.PicUrl + getIntent().getStringExtra("img")));
        homeBanner.setBannerData(bannerImg);
        homeBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                Glide.with(SecondActivity.this).load(bannerImg.get(position).getXBannerUrl()).into((ImageView) view);
            }
        });


        List<OneClassBean> list = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<List<OneClassBean>>() {
        }.getType());
        homeFlRv.setLayoutManager(new LinearLayoutManager(SecondActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter2 = new SecondFlAdapter(SecondActivity.this, R.layout.item_fl, list);
        homeFlRv.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                bannerImg = new ArrayList<>();
                if (list.get(position).getPic() != null) {
                    bannerImg.add(new CustomViewsInfo(Constans.PicUrl + list.get(position).getPic()));
                } else {
                    bannerImg.add(new CustomViewsInfo(Constans.PicUrl + ""));
                }
                homeBanner.setBannerData(bannerImg);
                homeBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {

                        //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                        //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                        Glide.with(SecondActivity.this).load(bannerImg.get(position).getXBannerUrl()).into((ImageView) view);
                    }
                });

                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                adapter2.notifyDataSetChanged();


                myChildObserver = new MyObserver<List<ChildBean>>(SecondActivity.this, true) {
                    @Override
                    public void onSuccess(List<ChildBean> result) {
                        if (result != null && result.size() > 0) {
                            topicViewPager.setVisibility(View.VISIBLE);
                            topicIndicator.setVisibility(View.VISIBLE);
                            initTypeViewPager(1, 5,result);
                            id = result.get(0).getId() + "";
                            page = 1;
                            myCommodityObserver = new MyObserver<CommodityListBean>(SecondActivity.this, true) {
                                @Override
                                public void onSuccess(CommodityListBean result) {
                                    if (result.getRows() != null && result.getRows().size() > 0) {
                                        homeRv.setVisibility(View.VISIBLE);
                                        adapter1 = new CommodityAdapter(SecondActivity.this, R.layout.item_commodity, result.getRows());
                                        homeRv.setAdapter(adapter1);

                                        adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                startActivity(new Intent(SecondActivity.this, DetailsActivity.class)
                                                        .putExtra("id", result.getRows().get(position).getId() + ""));
                                            }
                                        });
                                    } else {
                                        homeRv.setVisibility(View.INVISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(SecondActivity.this, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            ToastUtils.show(SecondActivity.this, errorMsg);
                                        }
                                    }
                                }
                            };

                            RetrofitUtils.getApiUrl().classCommodity(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", page + "", "10", result.get(0).getId() + "", DataHelper.getStringSF(SecondActivity.this, "token"))
                                    .compose(RxHelper.observableIO2Main(SecondActivity.this))
                                    .subscribe(myCommodityObserver);


                        } else {
                            homeRv.setVisibility(View.INVISIBLE);
                            topicViewPager.setVisibility(View.INVISIBLE);
                            topicIndicator.setVisibility(View.INVISIBLE);
                            id = null;
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(SecondActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(SecondActivity.this, errorMsg);
                            }
                        }
                    }
                };


                RetrofitUtils.getApiUrl().childClass(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", list.get(position).getId() + "", DataHelper.getStringSF(SecondActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(SecondActivity.this))
                        .subscribe(myChildObserver);


            }
        });


        marqueeView.setAdapter(new MarqueeViewAdapter(pmdData, SecondActivity.this));


        //secondRv.setLayoutManager(new LinearLayoutManager(SecondActivity.this, LinearLayoutManager.HORIZONTAL, false));


        homeRv.setLayoutManager(new GridLayoutManager(SecondActivity.this, 2));
        homeRv.addItemDecoration(new SpaceItemDecoration(2, ArmsUtils.dip2px(SecondActivity.this, 8), ArmsUtils.dip2px(SecondActivity.this, 10)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            homeBanner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                }
            });
            homeBanner.setClipToOutline(true);
        }


        myChildObserver = new MyObserver<List<ChildBean>>(SecondActivity.this, true) {
            @Override
            public void onSuccess(List<ChildBean> result) {
                if (result != null && result.size() > 0) {
                    topicViewPager.setVisibility(View.VISIBLE);
                    topicIndicator.setVisibility(View.VISIBLE);
                    initTypeViewPager(1, 5,result);
                    id = result.get(0).getId() + "";
                    page = 1;
                    myCommodityObserver = new MyObserver<CommodityListBean>(SecondActivity.this, true) {
                        @Override
                        public void onSuccess(CommodityListBean result) {
                            if (result.getRows() != null && result.getRows().size() > 0) {
                                homeRv.setVisibility(View.VISIBLE);
                                adapter1 = new CommodityAdapter(SecondActivity.this, R.layout.item_commodity, result.getRows());
                                homeRv.setAdapter(adapter1);

                                adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        startActivity(new Intent(SecondActivity.this, DetailsActivity.class)
                                                .putExtra("id", result.getRows().get(position).getId() + ""));
                                    }
                                });
                            } else {
                                homeRv.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                            if (e != null) {
                                ToastUtils.show(SecondActivity.this, errorCode);
                            } else {
                                if (errorCode.equals("401")) {
                                    Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    ToastUtils.show(SecondActivity.this, errorMsg);
                                }
                            }
                        }
                    };

                    RetrofitUtils.getApiUrl().classCommodity(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", page + "", "10", result.get(0).getId() + "", DataHelper.getStringSF(SecondActivity.this, "token"))
                            .compose(RxHelper.observableIO2Main(SecondActivity.this))
                            .subscribe(myCommodityObserver);

                } else {
                    homeRv.setVisibility(View.INVISIBLE);
                    topicViewPager.setVisibility(View.INVISIBLE);
                    topicIndicator.setVisibility(View.INVISIBLE);
                    id = null;
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(SecondActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(SecondActivity.this, errorMsg);
                    }
                }
            }
        };


        RetrofitUtils.getApiUrl().childClass(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", getIntent().getStringExtra("id"), DataHelper.getStringSF(SecondActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(SecondActivity.this))
                .subscribe(myChildObserver);


    }


    private void initTypeViewPager(int rowNum, int columnNum, List<ChildBean> result) {
        //1.根据数据的多少来分页，每页的数据为rw
        int singlePageDatasNum = rowNum * columnNum; //每个单页包含的数据量：2*4=8；
        int pageNum = result.size() / singlePageDatasNum;//算出有几页菜单：20%8 = 3;
        if (result.size() % singlePageDatasNum > 0) pageNum++;//如果取模大于0，就还要多一页出来，放剩下的不满项
        ArrayList<RecyclerView> mList = new ArrayList<>();
        for (int i = 0; i < pageNum; i++) {
            RecyclerView recyclerView = new RecyclerView(getApplicationContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), columnNum);
            recyclerView.setLayoutManager(gridLayoutManager);
            int fromIndex = i * singlePageDatasNum;
            int toIndex = (i + 1) * singlePageDatasNum;
            if (toIndex > result.size()) toIndex = result.size();
            //a.截取每个页面包含数据
            ArrayList<ChildBean> menuItems = new ArrayList<ChildBean>(result.subList(fromIndex, toIndex));
            //b.设置每个页面的适配器数据
            TopicAdapter menuAdapter = new TopicAdapter(getApplicationContext(), menuItems);
            menuAdapter.setOnItemClickListener(this);
            //c.绑定适配器，并添加到list
            recyclerView.setAdapter(menuAdapter);
            mList.add(recyclerView);
        }
        //2.ViewPager的适配器
        HomeTopicPagerAdapter menuViewPagerAdapter = new HomeTopicPagerAdapter(mList);
        topicViewPager.setAdapter(menuViewPagerAdapter);
        //3.动态设置ViewPager的高度，并加载所有页面
        int height = DimensionConvert.dip2px(getApplicationContext(), 76.0f);//这里的80为MainMenuAdapter中布局文件高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, result.size() <= columnNum ? height : height * rowNum);
        topicViewPager.setLayoutParams(layoutParams);
        topicViewPager.setOffscreenPageLimit(pageNum - 1);

        //4.创建指示器
        CommonNavigator commonNavigator = new CommonNavigator(getApplicationContext());
        commonNavigator.setAdjustMode(true);
        final int finalPageNum = pageNum;

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return finalPageNum;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return new DummyPagerTitleView(context);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));//就是指示器的高
                indicator.setLineWidth(UIUtil.dip2px(context, 66 / finalPageNum));//就是指示器的宽度，然后通过页数来评分
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(3));
                indicator.setColors(ContextCompat.getColor(context, R.color.main_color));
                return indicator;
            }
        });
        //5.配置指示器，并和ViewPager产生绑定
        topicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(topicIndicator, topicViewPager);
    }


    @Override
    protected void setListen() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, SearchActivity.class));
            }
        });

        jxTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    public void onTopicItemClick(ChildBean result) {
        id = result.getId() + "";
        page = 1;

        myCommodityObserver = new MyObserver<CommodityListBean>(SecondActivity.this, true) {
            @Override
            public void onSuccess(CommodityListBean result) {
                if (result.getRows() != null && result.getRows().size() > 0) {
                    homeRv.setVisibility(View.VISIBLE);
                    adapter1 = new CommodityAdapter(SecondActivity.this, R.layout.item_commodity, result.getRows());
                    homeRv.setAdapter(adapter1);

                    adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(SecondActivity.this, DetailsActivity.class)
                                    .putExtra("id", result.getRows().get(position).getId() + ""));
                        }
                    });
                } else {
                    homeRv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(SecondActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(SecondActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().classCommodity(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", page + "", "10", id + "", DataHelper.getStringSF(SecondActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(SecondActivity.this))
                .subscribe(myCommodityObserver);
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView childImg;
        private TextView childName;
        private LinearLayout childLin;

        public MyHolder(View itemView) {
            super(itemView);
            childImg = itemView.findViewById(R.id.itemChildImg);
            childName = itemView.findViewById(R.id.itemChildName);
            childLin = itemView.findViewById(R.id.itemSecondLin);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myChildObserver != null) {
            myChildObserver.cancleRequest();
        }
        if (myCommodityObserver != null) {
            myCommodityObserver.cancleRequest();
        }

    }

    @Override
    public void onLoadMore() {

        if (id != null) {

            page = page + 1;

            myCommodityObserver = new MyObserver<CommodityListBean>(SecondActivity.this, false) {
                @Override
                public void onSuccess(CommodityListBean result) {
                    swipeToLoadLayout.setLoadingMore(false);
                    if (result != null) {
                        loadRecyclerView(result.getRows());
                    }
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    swipeToLoadLayout.setLoadingMore(false);
                    if (e != null) {
                        ToastUtils.show(SecondActivity.this, errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(SecondActivity.this, errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().classCommodity(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", page + "", "10", id, DataHelper.getStringSF(SecondActivity.this, "token"))
                    .compose(RxHelper.observableIO2Main(SecondActivity.this))
                    .subscribe(myCommodityObserver);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }


    }

    private void loadRecyclerView(List<CommodityListBean.RowsBean> rows) {
        int position = adapter1.getItemCount() - 1;
        adapter1.loadMoreData(rows);
    }

    @Override
    public void onRefresh() {
        if (id != null) {
            page = 1;
            myCommodityObserver = new MyObserver<CommodityListBean>(SecondActivity.this, false) {
                @Override
                public void onSuccess(CommodityListBean result) {
                    swipeToLoadLayout.setRefreshing(false);
                    if (result.getRows() != null && result.getRows().size() > 0) {
                        homeRv.setVisibility(View.VISIBLE);
                        adapter1 = new CommodityAdapter(SecondActivity.this, R.layout.item_commodity, result.getRows());
                        homeRv.setAdapter(adapter1);

                        adapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(SecondActivity.this, DetailsActivity.class)
                                        .putExtra("id", result.getRows().get(position).getId() + ""));
                            }
                        });
                    } else {
                        homeRv.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    swipeToLoadLayout.setRefreshing(false);
                    if (e != null) {
                        ToastUtils.show(SecondActivity.this, errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(SecondActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(SecondActivity.this, errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().classCommodity(DataHelper.getIntergerSF(SecondActivity.this, "userType") + "", page + "", "10", id, DataHelper.getStringSF(SecondActivity.this, "token"))
                    .compose(RxHelper.observableIO2Main(SecondActivity.this))
                    .subscribe(myCommodityObserver);
        } else {
            swipeToLoadLayout.setRefreshing(false);
        }

    }

}
