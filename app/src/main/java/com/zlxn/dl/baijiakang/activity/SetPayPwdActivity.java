package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
 * @time 2020/10/23 11:12
 */
public class SetPayPwdActivity extends BaseActivity {


    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.loginPwdOldPwd)
    EditText loginPwdOldPwd;
    @BindView(R.id.loginPwdNewPwd)
    EditText loginPwdNewPwd;
    @BindView(R.id.loginPwdConfirmPwd)
    EditText loginPwdConfirmPwd;
    @BindView(R.id.setLoginPwdBtn)
    Button setLoginPwdBtn;
    @BindView(R.id.oldRel)
    RelativeLayout oldRel;
    @BindView(R.id.oldView)
    View oldView;
    private MyObserver<String> myObserver;
    private String oldPwd = "";

    @Override
    protected void initView() {
        setContentView(R.layout.act_pay_pwd);
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

        if (DataHelper.getBooleanSF(SetPayPwdActivity.this,"isPayPwd")){
            oldRel.setVisibility(View.VISIBLE);
            oldView.setVisibility(View.VISIBLE);
        }else {
            oldRel.setVisibility(View.GONE);
            oldView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void setListen() {
        setLoginPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataHelper.getBooleanSF(SetPayPwdActivity.this, "isPayPwd")){
                    if (loginPwdOldPwd.getText().toString().length() == 0 || loginPwdOldPwd.getText().toString().length() < 6) {
                        ToastUtils.show(SetPayPwdActivity.this, "请输入6位旧密码");
                        return;
                    }else {
                        oldPwd = loginPwdOldPwd.getText().toString();
                    }
                }else {
                    oldPwd = "";
                }

                if (loginPwdNewPwd.getText().toString().length() == 0 || loginPwdNewPwd.getText().toString().length() < 6) {
                    ToastUtils.show(SetPayPwdActivity.this, "请输入6位新密码");
                    return;
                }

                if (loginPwdConfirmPwd.getText().toString().length() == 0) {
                    ToastUtils.show(SetPayPwdActivity.this, "请输入确认密码");
                    return;
                }

                if (!loginPwdNewPwd.getText().toString().equals(loginPwdConfirmPwd.getText().toString())) {
                    ToastUtils.show(SetPayPwdActivity.this, "两次密码输入不一致");
                    return;
                }

                myObserver = new MyObserver<String>(SetPayPwdActivity.this, true) {
                    @Override
                    public void onSuccess(String result) {
                        ToastUtils.show(SetPayPwdActivity.this, "修改支付密码成功");
                        DataHelper.setBooleanSF(SetPayPwdActivity.this,"isPayPwd",true);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(SetPayPwdActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(SetPayPwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(SetPayPwdActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().setPayPwd(DataHelper.getIntergerSF(SetPayPwdActivity.this, "userType") + "", oldPwd,
                        loginPwdNewPwd.getText().toString(), DataHelper.getStringSF(SetPayPwdActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(SetPayPwdActivity.this))
                        .subscribe(myObserver);

            }
        });

    }

}
