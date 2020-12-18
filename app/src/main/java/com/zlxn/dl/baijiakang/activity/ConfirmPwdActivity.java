package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.cirno_poi.verifyedittextlibrary.VerifyEditText;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/23 11:35
 */
public class ConfirmPwdActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.payEdit)
    VerifyEditText payEdit;

    @Override
    protected void initView() {
        setContentView(R.layout.act_confirm_pwd);
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
        payEdit.setInputCompleteListener(new VerifyEditText.inputCompleteListener() {
            @Override
            public void inputComplete(VerifyEditText et, String content) {
              finish();
            }
        });
    }

}
