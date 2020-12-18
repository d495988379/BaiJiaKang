package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.utils.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/23 10:16
 */
public class ImproveInformationActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.informationImg)
    ImageView informationImg;
    @BindView(R.id.informationBtn)
    Button informationBtn;

    @Override
    protected void initView() {
        setContentView(R.layout.act_information);
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


        CodeUtils codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        informationImg.setImageBitmap(bitmap);
    }

    @Override
    protected void setListen() {
        informationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CodeUtils codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                informationImg.setImageBitmap(bitmap);
            }
        });

        informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImproveInformationActivity.this,HomeActivity.class));
            }
        });
    }

}
