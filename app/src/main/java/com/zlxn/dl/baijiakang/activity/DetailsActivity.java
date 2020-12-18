package com.zlxn.dl.baijiakang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.stx.xhb.androidx.XBanner;
import com.stx.xmarqueeview.XMarqueeView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.MarqueeViewAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.CommodityDetailsBean;
import com.zlxn.dl.baijiakang.bean.CustomViewsInfo;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.http.Constans;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/21 14:40
 */
public class DetailsActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.upView)
    XMarqueeView marqueeView;
    @BindView(R.id.detailsGgRel)
    RelativeLayout detailsGgRel;
    @BindView(R.id.detailsBottomLin)
    LinearLayout detailsBottomLin;
    @BindView(R.id.detailsPjRel)
    RelativeLayout detailsPjRel;
    @BindView(R.id.detailsShopCar)
    LinearLayout detailsShopCar;
    @BindView(R.id.detailsBanner)
    XBanner detailsBanner;
    @BindView(R.id.detailsMoney)
    TextView detailsMoney;
    @BindView(R.id.detailsJy)
    TextView detailsJy;
    @BindView(R.id.detailsName)
    TextView detailsName;
    @BindView(R.id.detailsTotal)
    TextView detailsTotal;
    @BindView(R.id.detailsWeb)
    WebView webView;
    @BindView(R.id.detailsAddCarBtn)
    Button detailsAddCarBtn;
    @BindView(R.id.detailsBuyBtn)
    Button detailsBuyBtn;
    @BindView(R.id.detailsQuan)
    TextView detailsQuan;
    @BindView(R.id.detailsFan)
    TextView detailsFan;
    private List<String> pmdData = new ArrayList<>();
    private MyObserver<CommodityDetailsBean> myObserver;
    private List<CustomViewsInfo> bannerImg;
    private MyObserver<String> myAddObserver;
    private String id;
    private CommodityDetailsBean bean;
    private List<ShopCarBean.DataBean> spendData;
    private String js;
    private List<String> gg;
    private GgPopupView ggPopupView;
    private int count = 1; //商品数量
    private String ggUploadMoney = "";//规格单价
    private String ggName = "";//规格名称
    private int ggId;//规格id
    private int ggUploadQuan = 0;//
    private BigDecimal bg;

    @Override
    protected void initView() {
        setContentView(R.layout.act_details);
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

        ggPopupView = new GgPopupView(DetailsActivity.this);
        pmdData.add("周莘羽刚刚购买云南白药1");
        pmdData.add("周莘羽刚刚购买云南白药2");
        pmdData.add("周莘羽刚刚购买云南白药3");
        pmdData.add("周莘羽刚刚购买云南白药4");


        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        webView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        webView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.NORMAL);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
//设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);//设置js可用
        webView.setWebViewClient(new WebViewClient());
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局


        js = "<script type=\"text/javascript\">" +
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";

        marqueeView.setAdapter(new MarqueeViewAdapter(pmdData, DetailsActivity.this));


        myObserver = new MyObserver<CommodityDetailsBean>(this, true) {
            @Override
            public void onSuccess(CommodityDetailsBean result) {
                bean = result;
                detailsJy.setText("建议零售价：¥" + result.getProduct().getOriginalPrice());
                detailsName.setText(result.getProduct().getProductName());
                detailsTotal.setText(result.getProductNumber() + "");
                detailsQuan.setText(result.getProduct().getCardRoll() + "");
                detailsFan.setText(result.getProduct().getRewind());
                detailsMoney.setText(result.getProduct().getPrice());


                if (result.getProduct().getProductContent() != null) {
                    String data = "<html><body>" + result.getProduct().getProductContent() + "</body></html>";
                    webView.loadDataWithBaseURL(null, data + js, "text/html", "utf-8", null);
                }

                if (result.getSpecification() != null && result.getSpecification().size() > 0) {
                    gg = new ArrayList<>();
                    ggId = result.getSpecification().get(0).getId();
                    if (DataHelper.getIntergerSF(DetailsActivity.this,"userType") == 1){
                        if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                            ggUploadMoney = result.getSpecification().get(0).getMunicipalityAgency();

                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                            ggUploadMoney = result.getSpecification().get(0).getProvincialCapital();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                            ggUploadMoney = result.getSpecification().get(0).getPrefectureLevelCity();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                            ggUploadMoney = result.getSpecification().get(0).getProvincialCapitalCounties();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 5){
                            ggUploadMoney = result.getSpecification().get(0).getDeputyProvincialCapital();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 6){
                            ggUploadMoney = result.getSpecification().get(0).getDeputyProvincialCapitalCounties();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 7){
                            ggUploadMoney = result.getSpecification().get(0).getMunicipalityAgencyCounties();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 8){
                            ggUploadMoney = result.getSpecification().get(0).getPrefectureLevelCityCounties();
                        }
                    }else {
                        if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                            ggUploadMoney = result.getSpecification().get(0).getPukaPrice();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                            ggUploadMoney = result.getSpecification().get(0).getSilverPrice();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                            ggUploadMoney = result.getSpecification().get(0).getGoldPrice();
                        }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                            ggUploadMoney = result.getSpecification().get(0).getPrice();
                        }
                    }

                    ggName = result.getSpecification().get(0).getSpecification();
                    ggUploadQuan = result.getSpecification().get(0).getCardRoll();
                    for (int i = 0; i < result.getSpecification().size(); i++) {
                        gg.add(result.getSpecification().get(i).getSpecification());
                    }
                }

                bannerImg = new ArrayList<>();
                bannerImg.add(new CustomViewsInfo(Constans.PicUrl + result.getProduct().getPic()));
                detailsBanner.setBannerData(bannerImg);

                detailsBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {

                        //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                        //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                        Glide.with(DetailsActivity.this).load(bannerImg.get(position).getXBannerUrl()).into((ImageView) view);
                    }
                });

                detailsBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, Object model, View view, int position) {
                    }
                });


            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(DetailsActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(DetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(DetailsActivity.this, errorMsg);
                    }
                }
            }
        };


        RetrofitUtils.getApiUrl().commodityDetails(DataHelper.getIntergerSF(DetailsActivity.this, "userType") + "", id, DataHelper.getStringSF(DetailsActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(DetailsActivity.this))
                .subscribe(myObserver);


    }

    @Override
    protected void setListen() {
        detailsGgRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(DetailsActivity.this)
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(ggPopupView)/*.enableDrag(false)*/
                        .show();

            }
        });

        detailsAddCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(DetailsActivity.this)
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(ggPopupView)/*.enableDrag(false)*/
                        .show();
            }
        });


        detailsPjRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, PjActivity.class)
                        .putExtra("id", bean.getProduct().getId() + ""));
            }
        });

        detailsShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, ShoppingCarActivity.class));
            }
        });


        detailsBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new XPopup.Builder(DetailsActivity.this)
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(ggPopupView)/*.enableDrag(false)*/
                        .show();
            }
        });
    }


    public class GgPopupView extends BottomPopupView {

        public GgPopupView(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_gg;
        }

        @Override
        protected void onCreate() {
            LabelsView labelsView = findViewById(R.id.gg_labels);
            ImageView ggImg = findViewById(R.id.ggImg);
            ImageView ggJian = findViewById(R.id.ggJian);
            ImageView ggAdd = findViewById(R.id.ggAdd);
            TextView commodityCount = findViewById(R.id.commodityCount);
            TextView ggMoney = findViewById(R.id.ggMoney);
            TextView ggQuan = findViewById(R.id.ggQuan);
            TextView ggJyMoney = findViewById(R.id.ggJyMoney);
            TextView ggFan = findViewById(R.id.ggFan);
            LinearLayout detailsShopCar = findViewById(R.id.detailsShopCar);
            Button detailsAddCarBtn = findViewById(R.id.detailsAddCarBtn);
            Button detailsBuyBtn = findViewById(R.id.detailsBuyBtn);
            

            Glide.with(DetailsActivity.this).load(Constans.PicUrl + bean.getProduct().getPic()).into(ggImg);
            labelsView.setLabels(gg);
            ggFan.setText(bean.getSpecification().get(0).getRewind());


            if (DataHelper.getIntergerSF(DetailsActivity.this,"userType") == 1){
                if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                    ggMoney.setText(bean.getSpecification().get(0).getMunicipalityAgency());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                    ggMoney.setText(bean.getSpecification().get(0).getProvincialCapital());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                    ggMoney.setText(bean.getSpecification().get(0).getPrefectureLevelCity());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                    ggMoney.setText(bean.getSpecification().get(0).getProvincialCapitalCounties());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 5){
                    ggMoney.setText(bean.getSpecification().get(0).getDeputyProvincialCapital());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 6){
                    ggMoney.setText(bean.getSpecification().get(0).getDeputyProvincialCapitalCounties());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 7){
                    ggMoney.setText(bean.getSpecification().get(0).getMunicipalityAgencyCounties());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 8){
                    ggMoney.setText(bean.getSpecification().get(0).getPrefectureLevelCityCounties());
                }
            }else {
                if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                    ggMoney.setText(bean.getSpecification().get(0).getPukaPrice());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                    ggMoney.setText(bean.getSpecification().get(0).getSilverPrice());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                    ggMoney.setText(bean.getSpecification().get(0).getGoldPrice());
                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                    ggMoney.setText(bean.getSpecification().get(0).getPrice());
                }
            }



            ggQuan.setText(bean.getSpecification().get(0).getCardRoll() + "");
            ggJyMoney.setText("建议零售价：¥" + bean.getSpecification().get(0).getOriginalPrice());



            ggJian.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = Integer.parseInt(commodityCount.getText().toString());
                    if (count > 1) {
                        count--;
                        commodityCount.setText(count + "");
                    }
                }
            });


            ggAdd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = Integer.parseInt(commodityCount.getText().toString());
                    count++;
                    commodityCount.setText(count + "");
                }
            });


            detailsShopCar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    startActivity(new Intent(DetailsActivity.this, ShoppingCarActivity.class));
                }
            });

            //添加购物车
            detailsAddCarBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    myAddObserver = new MyObserver<String>(DetailsActivity.this, true) {
                        @Override
                        public void onSuccess(String result) {
                            dismiss();
                            ToastUtils.show(DetailsActivity.this, "添加购物车成功");
                        }

                        @Override
                        public void onFailure(Throwable e, String errorCode, String errorMsg) {
                            if (e != null) {
                                ToastUtils.show(DetailsActivity.this, errorCode);
                            } else {
                                if (errorCode.equals("401")) {
                                    Intent intent = new Intent(DetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    ToastUtils.show(DetailsActivity.this, errorMsg);
                                }
                            }
                        }
                    };
                    RetrofitUtils.getApiUrl().addCar(DataHelper.getIntergerSF(DetailsActivity.this, "userType") + "", id, count + "", ggId + "", ggUploadQuan+"", DataHelper.getStringSF(DetailsActivity.this, "token"))
                            .compose(RxHelper.observableIO2Main(DetailsActivity.this))
                            .subscribe(myAddObserver);
                }
            });

            //立即购买
            detailsBuyBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    spendData = new ArrayList<>();
                    ShopCarBean.DataBean spendBean = new ShopCarBean.DataBean();
                    spendBean.setNumber(count);
                    spendBean.setId(bean.getProduct().getId());
                    spendBean.setPic(bean.getProduct().getPic());
                    spendBean.setPrice(ggUploadMoney);
                    spendBean.setProductName(bean.getProduct().getProductName());
                    spendBean.setSpecification(ggName);
                    spendBean.setTicket(ggUploadQuan);
                    spendBean.setStageId(ggId + "");
                    spendData.add(spendBean);

                    double totalMoney = Double.parseDouble(ggUploadMoney) * count;
                    int totalQuan = ggUploadQuan * count;

                    bg = new BigDecimal(totalMoney);
                    double flMoney = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    startActivity(new Intent(DetailsActivity.this, ConfirmOrderActivity.class)
                            .putExtra("data", new Gson().toJson(spendData))
                            .putExtra("money", flMoney + "")
                            .putExtra("quan", totalQuan + "")
                            .putExtra("type", "2"));
                }
            });

            labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
                @Override
                public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                    for (int i = 0; i < bean.getSpecification().size(); i++) {
                        if (label.getText().toString().equals(bean.getSpecification().get(i).getSpecification())) {
                            ggJyMoney.setText("建议零售价：¥" + bean.getSpecification().get(i).getOriginalPrice());
                            ggQuan.setText(bean.getSpecification().get(i).getCardRoll() + "");
                            ggFan.setText(bean.getSpecification().get(i).getRewind());
                            ggId = bean.getSpecification().get(i).getId();

                            if (DataHelper.getIntergerSF(DetailsActivity.this,"userType") == 1){

                                if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                                    ggUploadMoney = bean.getSpecification().get(i).getMunicipalityAgency();
                                    ggMoney.setText(bean.getSpecification().get(i).getMunicipalityAgency());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                                    ggUploadMoney = bean.getSpecification().get(i).getProvincialCapital();
                                    ggMoney.setText(bean.getSpecification().get(i).getProvincialCapital());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                                    ggUploadMoney = bean.getSpecification().get(i).getPrefectureLevelCity();
                                    ggMoney.setText(bean.getSpecification().get(i).getPrefectureLevelCity());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                                    ggUploadMoney = bean.getSpecification().get(i).getProvincialCapitalCounties();
                                    ggMoney.setText(bean.getSpecification().get(i).getProvincialCapitalCounties());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 5){
                                    ggUploadMoney = bean.getSpecification().get(i).getDeputyProvincialCapital();
                                    ggMoney.setText(bean.getSpecification().get(i).getDeputyProvincialCapital());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 6){
                                    ggUploadMoney = bean.getSpecification().get(i).getDeputyProvincialCapitalCounties();
                                    ggMoney.setText(bean.getSpecification().get(i).getDeputyProvincialCapitalCounties());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 7){
                                    ggUploadMoney = bean.getSpecification().get(i).getMunicipalityAgencyCounties();
                                    ggMoney.setText(bean.getSpecification().get(i).getMunicipalityAgencyCounties());
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 8){
                                    ggUploadMoney = bean.getSpecification().get(i).getPrefectureLevelCityCounties();
                                    ggMoney.setText(bean.getSpecification().get(i).getPrefectureLevelCityCounties());
                                }
                            }else {

                                if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 1){
                                    ggMoney.setText(bean.getSpecification().get(i).getPukaPrice());
                                    ggUploadMoney = bean.getSpecification().get(i).getPukaPrice();
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 2){
                                    ggMoney.setText(bean.getSpecification().get(i).getSilverPrice());
                                    ggUploadMoney = bean.getSpecification().get(i).getSilverPrice();
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 3){
                                    ggMoney.setText(bean.getSpecification().get(i).getGoldPrice());
                                    ggUploadMoney = bean.getSpecification().get(i).getGoldPrice();
                                }else if (DataHelper.getIntergerSF(DetailsActivity.this,"level") == 4){
                                    ggMoney.setText(bean.getSpecification().get(i).getPrice());
                                    ggUploadMoney = bean.getSpecification().get(i).getPrice();
                                }

                            }

                            ggName = bean.getSpecification().get(i).getSpecification();
                            ggUploadQuan = bean.getSpecification().get(i).getCardRoll();

                        }
                    }
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
        if (myAddObserver != null) {
            myAddObserver.cancleRequest();
        }
    }
}
