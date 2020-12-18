package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jess.arms.utils.DataHelper;
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
 * @time 2020/10/23 10:52
 */
public class SetLoginPwdActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.setLoginPwdBtn)
    Button setLoginPwdBtn;
    @BindView(R.id.loginPwdPhone)
    EditText loginPwdPhone;
    @BindView(R.id.loginPwdOldPwd)
    EditText loginPwdOldPwd;
    @BindView(R.id.loginPwdNewPwd)
    EditText loginPwdNewPwd;
    @BindView(R.id.loginPwdConfirmPwd)
    EditText loginPwdConfirmPwd;
    private MyObserver<String> myObserver;

    @Override
    protected void initView() {
        setContentView(R.layout.act_login_pwd);
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
    }

    @Override
    protected void setListen() {
        setLoginPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginPwdOldPwd.getText().toString().length() == 0){
                    ToastUtils.show(SetLoginPwdActivity.this,"请输入旧密码");
                    return;
                }
                if (loginPwdNewPwd.getText().toString().length() == 0){
                    ToastUtils.show(SetLoginPwdActivity.this,"请输入新密码");
                    return;
                }

                if (loginPwdConfirmPwd.getText().toString().length() == 0){
                    ToastUtils.show(SetLoginPwdActivity.this,"请输入确认密码");
                    return;
                }

                if (!loginPwdNewPwd.getText().toString().equals(loginPwdConfirmPwd.getText().toString())){
                    ToastUtils.show(SetLoginPwdActivity.this,"两次密码输入不一致");
                    return;
                }

                myObserver = new MyObserver<String>(SetLoginPwdActivity.this,true) {
                    @Override
                    public void onSuccess(String result) {
                        ToastUtils.show(SetLoginPwdActivity.this,"修改成功");
                        DataHelper.setBooleanSF(SetLoginPwdActivity.this,"isLogin",false);
                        Intent intent = new Intent(SetLoginPwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(SetLoginPwdActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(SetLoginPwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(SetLoginPwdActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().setLoginPwd(DataHelper.getIntergerSF(SetLoginPwdActivity.this, "userType") + "",loginPwdOldPwd.getText().toString(),
                        loginPwdNewPwd.getText().toString(),DataHelper.getStringSF(SetLoginPwdActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(SetLoginPwdActivity.this))
                        .subscribe(myObserver);

            }
        });
    }

}
