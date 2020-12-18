package com.zlxn.dl.baijiakang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.RechargeAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/14 14:53
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.hfRechargeTv)
    TextView hfRechargeTv;
    @BindView(R.id.hfRechargeView)
    View hfRechargeView;
    @BindView(R.id.ykRechargeTv)
    TextView ykRechargeTv;
    @BindView(R.id.ykRechargeView)
    View ykRechargeView;
    @BindView(R.id.jlRechargeTv)
    TextView jlRechargeTv;
    @BindView(R.id.jlRechargeView)
    View jlRechargeView;
    @BindView(R.id.hfLin)
    LinearLayout hfLin;
    @BindView(R.id.ykLin)
    LinearLayout ykLin;
    @BindView(R.id.jlRv)
    RecyclerView jlRv;
    @BindView(R.id.jlLin)
    LinearLayout jlLin;

    private List<String> data = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.act_recharge);
    }

    @Override
    protected void initData() {

        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");

        commonBar.setBackgroundResource(R.color.main_color);

        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        jlRv.setLayoutManager(new LinearLayoutManager(this));
        RechargeAdapter adapter = new RechargeAdapter(this,R.layout.item_recharge,data);
        jlRv.setAdapter(adapter);

    }

    @Override
    protected void setListen() {
        hfRechargeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hfRechargeView.setVisibility(View.VISIBLE);
                ykRechargeView.setVisibility(View.GONE);
                jlRechargeView.setVisibility(View.GONE);

                hfLin.setVisibility(View.VISIBLE);
                ykLin.setVisibility(View.GONE);
                jlLin.setVisibility(View.GONE);
            }
        });

        ykRechargeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hfRechargeView.setVisibility(View.GONE);
                ykRechargeView.setVisibility(View.VISIBLE);
                jlRechargeView.setVisibility(View.GONE);

                hfLin.setVisibility(View.GONE);
                ykLin.setVisibility(View.VISIBLE);
                jlLin.setVisibility(View.GONE);
            }
        });
        jlRechargeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hfRechargeView.setVisibility(View.GONE);
                ykRechargeView.setVisibility(View.GONE);
                jlRechargeView.setVisibility(View.VISIBLE);

                hfLin.setVisibility(View.GONE);
                ykLin.setVisibility(View.GONE);
                jlLin.setVisibility(View.VISIBLE);
            }
        });

    }

}
