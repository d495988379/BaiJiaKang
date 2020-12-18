package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.DataHelper;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 16:29
 */
public class AddressAddActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.addressAddBtn)
    Button addressAddBtn;
    @BindView(R.id.addressAddUser)
    EditText addressAddUser;
    @BindView(R.id.addressAddPhone)
    EditText addressAddPhone;
    @BindView(R.id.addressAddCity)
    TextView addressAddCity;
    @BindView(R.id.addressAddAddress)
    EditText addressAddAddress;
    private MyObserver<String> myObserver;
    private JDCityPicker cityPicker;
    private String uploadContent = "";
    private String cityS = "";

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.act_address_add);
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


        cityPicker = new JDCityPicker();
        JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
        jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
        cityPicker.init(this);
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                if (province.getName().equals("天津市")){
                    cityS = "天津";
                }else if (province.getName().equals("北京市")){
                    cityS = "北京";
                }else if (province.getName().equals("上海市")){
                    cityS = "上海";
                }else if (province.getName().equals("重庆市")){
                    cityS = "重庆";
                }else{
                    cityS = province.getName();
                }

                addressAddCity.setText(cityS + city.getName() + district.getName());
                uploadContent = cityS + "," + city.getName() + "," + district.getName();
            }

            @Override
            public void onCancel() {
            }
        });

       /* myObserver = new MyObserver<String>(AddressAddActivity.this,true) {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {

            }
        };

        RetrofitUtils.getApiUrl().regionManage(DataHelper.getStringSF(AddressAddActivity.this,"token"))
                .compose(RxHelper.observableIO2Main(AddressAddActivity.this))
                .subscribe(myObserver);*/
    }

    @Override
    protected void setListen() {
        addressAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressAddUser.getText().toString().length() == 0){
                    ToastUtils.show(AddressAddActivity.this,"请输入收货人");
                    return;
                }
                if (addressAddPhone.getText().toString().length() == 0){
                    ToastUtils.show(AddressAddActivity.this,"请输入手机号码");
                    return;
                }
                if (addressAddCity.getText().toString().length() == 0){
                    ToastUtils.show(AddressAddActivity.this,"请输入所在地区");
                    return;
                }
                if (addressAddAddress.getText().toString().length() == 0){
                    ToastUtils.show(AddressAddActivity.this,"请输入详细地址");
                    return;
                }

                myObserver = new MyObserver<String>(AddressAddActivity.this,true) {
                    @Override
                    public void onSuccess(String result) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(AddressAddActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(AddressAddActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(AddressAddActivity.this,errorMsg);
                            }
                        }
                    }
                };


                RetrofitUtils.getApiUrl().addAddress(DataHelper.getIntergerSF(AddressAddActivity.this, "userType") + "",addressAddUser.getText().toString(),addressAddPhone.getText().toString(),
                        uploadContent,addressAddAddress.getText().toString(), DataHelper.getStringSF(AddressAddActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(AddressAddActivity.this))
                        .subscribe(myObserver);

            }
        });

        addressAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityPicker.showCityPicker();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null){
            myObserver.cancleRequest();
        }

    }
}
