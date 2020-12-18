package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/23 9:46
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.setLoginPwd)
    TextView setLoginPwd;
    @BindView(R.id.setPayPwd)
    TextView setPayPwd;

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting);
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
        setLoginPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,SetLoginPwdActivity.class));
            }
        });

        setPayPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,SetPayPwdActivity.class));
            }
        });
    }
}
