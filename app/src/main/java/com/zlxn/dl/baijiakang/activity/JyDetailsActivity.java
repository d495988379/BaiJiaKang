package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.JyDetailsBean;
import com.zlxn.dl.baijiakang.bean.JyListBean;
import com.zlxn.dl.baijiakang.bean.JyPayBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/1 17:33
 */
public class JyDetailsActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.jyLabels1)
    LabelsView jyLabels1;
    @BindView(R.id.jyLabels2)
    LabelsView jyLabels2;
    @BindView(R.id.itemJyImg)
    ImageView itemJyImg;
    @BindView(R.id.jyName)
    TextView jyName;
    @BindView(R.id.jyAddress)
    TextView jyAddress;
    @BindView(R.id.jyMoney)
    TextView jyMoney;
    @BindView(R.id.jyJl)
    TextView jyJl;
    @BindView(R.id.jyYh)
    TextView jyYh;
    @BindView(R.id.jyBtn)
    Button jyBtn;
    @BindView(R.id.jyZyTv)
    TextView jyZyTv;
    private List<String> name = new ArrayList<>();
    private List<String> gun = new ArrayList<>();
    private MyObserver<JyDetailsBean> myObserver;
    private MyObserver<JyPayBean> myBtnObserver;
    private List<JyDetailsBean.DataBean.OilPriceListBean> oilPriceList;
    private String oilNum = "";
    private String oilGun = "";
    private String id;

    @Override
    protected void initView() {
        setContentView(R.layout.act_jy_details);
    }

    @Override
    protected void initData() {
        commonBar.setBackgroundResource(R.color.main_color);


        jyZyTv.setText(Html.fromHtml("<font color= '#D7433A'>" + "温馨提示：</font>" + "请务必先到达油站与工作人员确认后再付款，切勿先买单后加油，避免异常订单的产生。若无您选择的油枪号，请联系油站工作人。支付前请确认是否正确。"));

        String data = getIntent().getStringExtra("data");
        JyListBean.DataBean bean = new Gson().fromJson(data, JyListBean.DataBean.class);


        Glide.with(this).load(bean.getGaslogosmall()).into(itemJyImg);
        jyName.setText(bean.getGasName());
        jyAddress.setText(bean.getAddress());

        BigDecimal bg = new BigDecimal(Double.parseDouble(bean.getDistance()) / 1000);
        double distance = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        jyJl.setText("距您" + distance + "千米");

        id = getIntent().getStringExtra("id");

        myObserver = new MyObserver<JyDetailsBean>(JyDetailsActivity.this, true) {
            @Override
            public void onSuccess(JyDetailsBean result) {
                if (result.getData() != null && result.getData().size() > 0) {
                    if (result.getData().get(0).getOilPriceList() != null && result.getData().get(0).getOilPriceList().size() > 0) {
                        oilPriceList = result.getData().get(0).getOilPriceList();
                        name = new ArrayList<>();
                        gun = new ArrayList<>();
                        jyMoney.setText(result.getData().get(0).getOilPriceList().get(0).getPriceYfq() + "元/升");
                        double moneyGun = Double.parseDouble(result.getData().get(0).getOilPriceList().get(0).getPriceGun());
                        double moneyYfq = Double.parseDouble(result.getData().get(0).getOilPriceList().get(0).getPriceYfq());
                        BigDecimal bg = new BigDecimal((moneyGun - moneyYfq));
                        double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                        jyYh.setText("比油站价降¥" + money);

                        for (int i = 0; i < result.getData().get(0).getOilPriceList().size(); i++) {
                            name.add(result.getData().get(0).getOilPriceList().get(i).getOilName());
                            if (result.getData().get(0).getOilPriceList().get(i).getGunNos() != null && result.getData().get(0).getOilPriceList().get(i).getGunNos().size() > 0) {
                                for (int i1 = 0; i1 < result.getData().get(0).getOilPriceList().get(i).getGunNos().size(); i1++) {
                                    gun.add(result.getData().get(0).getOilPriceList().get(i).getGunNos().get(i1).getGunNo() + "");
                                }
                            }
                        }

                        jyLabels1.setLabels(name);
                        jyLabels2.setLabels(gun);
                    }
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(JyDetailsActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(JyDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(JyDetailsActivity.this, errorMsg);
                    }
                }

            }
        };


        RetrofitUtils.getApiUrl().jyDetails(id,DataHelper.getStringSF(JyDetailsActivity.this,"userPhone") ,DataHelper.getStringSF(JyDetailsActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(JyDetailsActivity.this))
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


        jyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBtnObserver = new MyObserver<JyPayBean>(JyDetailsActivity.this, true) {
                    @Override
                    public void onSuccess(JyPayBean result) {
                        if (result.getData() != null && !result.getData().equals("")) {
                            startActivity(new Intent(JyDetailsActivity.this, BannerWebActivity.class)
                                    .putExtra("url", result.getData()));
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(JyDetailsActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(JyDetailsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(JyDetailsActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().petrolAuth(id, DataHelper.getStringSF(JyDetailsActivity.this, "userPhone"), oilGun, oilNum, DataHelper.getStringSF(JyDetailsActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(JyDetailsActivity.this))
                        .subscribe(myBtnObserver);
            }
        });


        jyLabels1.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {

                oilNum = name.get(position).substring(0, name.get(position).length() - 1);
                jyMoney.setText(oilPriceList.get(position).getPriceYfq() + "元/升");
                double moneyGun = Double.parseDouble(oilPriceList.get(position).getPriceGun());
                double moneyYfq = Double.parseDouble(oilPriceList.get(position).getPriceYfq());


                BigDecimal bg = new BigDecimal((moneyGun - moneyYfq));
                double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


                jyYh.setText("比油站价降¥" + money);


                if (oilPriceList.get(position).getGunNos() != null && oilPriceList.get(position).getGunNos().size() > 0) {
                    gun = new ArrayList<>();
                    for (int i = 0; i < oilPriceList.get(position).getGunNos().size(); i++) {
                        gun.add(oilPriceList.get(position).getGunNos().get(i).getGunNo() + "");
                    }
                    jyLabels2.setLabels(gun);

                }
            }
        });


        jyLabels2.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                oilGun = gun.get(position);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }

}
