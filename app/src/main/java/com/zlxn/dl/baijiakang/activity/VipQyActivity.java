package com.zlxn.dl.baijiakang.activity;

import android.os.Bundle;
import android.view.View;

import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/16 15:50
 */
public class VipQyActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;

    @Override
    protected void initView() {
        setContentView(R.layout.act_vip);
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
}
