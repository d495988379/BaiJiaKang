package com.zlxn.dl.baijiakang.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.utils.DataHelper;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.LoginBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    private MyObserver<LoginBean> myObserver;
    private View loginStateBar;
    private Button loginBtn;
    private ImageView loginBack, wxLogin;
    private EditText loginUser, loginPwd;

    @Override
    protected void initView() {
        if (DataHelper.getBooleanSF(this, "isLogin")) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            setContentView(R.layout.act_login);
            initBinge();
        }

    }

    private void initBinge() {
        loginStateBar = findViewById(R.id.loginStateBar);
        loginBack = findViewById(R.id.loginBack);
        wxLogin = findViewById(R.id.wxLogin);
        loginBtn = findViewById(R.id.loginBtn);
        loginUser = findViewById(R.id.loginUser);
        loginPwd = findViewById(R.id.loginPwd);


        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) loginStateBar.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        loginStateBar.setLayoutParams(lp);


        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginUser.getText().toString().length() == 0) {
                    ToastUtils.show(LoginActivity.this, "请输入代理账户名");
                    return;
                }
                if (loginPwd.getText().toString().length() == 0) {
                    ToastUtils.show(LoginActivity.this, "请输入代理密码");
                    return;
                }
                myObserver = new MyObserver<LoginBean>(LoginActivity.this, true) {

                    @Override
                    public void onSuccess(LoginBean result) {
                        if (result.getUser().getPayPassword() != null && !result.getUser().getPayPassword().equals("")) {
                            DataHelper.setBooleanSF(LoginActivity.this, "isPayPwd", true);
                        } else {
                            DataHelper.setBooleanSF(LoginActivity.this, "isPayPwd", false);
                        }

                        DataHelper.setStringSF(LoginActivity.this, "userName", result.getUser().getWechatName() != null ? result.getUser().getWechatName() : "");
                        DataHelper.setStringSF(LoginActivity.this, "userHead", result.getUser().getPic() != null ? result.getUser().getPic() : "");
                        DataHelper.setStringSF(LoginActivity.this, "userPhone", result.getUser().getPhone() != null ? result.getUser().getPhone() : "");
                        DataHelper.setStringSF(LoginActivity.this, "token", result.getToken());
                        DataHelper.setBooleanSF(LoginActivity.this, "isLogin", true);
                        DataHelper.setIntergerSF(LoginActivity.this, "userType", result.getUserType());//1代理商  2用户
                        DataHelper.setIntergerSF(LoginActivity.this, "level", result.getLevel());
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(LoginActivity.this, errorCode);
                        } else {
                            ToastUtils.show(LoginActivity.this, errorMsg);
                        }

                    }
                };


                RetrofitUtils.getApiUrl().login("1", loginPwd.getText().toString(), loginUser.getText().toString())
                        .compose(RxHelper.observableIO2Main(LoginActivity.this))
                        .subscribe(myObserver);

            }
        });

        wxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ImproveInformationActivity.class));
            }
        });

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


        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);

        }


    }

    @Override
    protected void setListen() {

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
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }

}
