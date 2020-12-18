package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.BalanceAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.BalanceRecordsBean;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/22 14:15
 */
public class MineBalanceActivity extends BaseActivity implements OnLoadMoreListener {


    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.balanceRv)
    RecyclerView balanceRv;
    @BindView(R.id.mineBalanceWithdrawal)
    TextView mineBalanceWithdrawal;
    @BindView(R.id.mineBalanceMoney)
    TextView mineBalanceMoney;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int page = 1;
    private MyObserver<BalanceRecordsBean> myObserver;
    private BalanceAdapter adapter;
    private String makeMoney;
    private String balance;

    @Override
    protected void initView() {
        setContentView(R.layout.act_balance);
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

        swipeToLoadLayout.setTargetView(balanceRv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);



        balanceRv.setLayoutManager(new LinearLayoutManager(this));
        balance = getIntent().getStringExtra("balance");
        makeMoney = getIntent().getStringExtra("makeMoney");

        mineBalanceMoney.setText((Double.parseDouble(balance) + Double.parseDouble(makeMoney)) + "元");

        myObserver = new MyObserver<BalanceRecordsBean>(MineBalanceActivity.this, true) {
            @Override
            public void onSuccess(BalanceRecordsBean result) {
                if (result.getData() != null && result.getData().size() > 0) {
                    adapter = new BalanceAdapter(MineBalanceActivity.this, R.layout.item_balance, result.getData());
                    balanceRv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(MineBalanceActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(MineBalanceActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(MineBalanceActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().checkRecords(DataHelper.getIntergerSF(MineBalanceActivity.this, "userType") + "", page + "", "10", DataHelper.getStringSF(MineBalanceActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(MineBalanceActivity.this))
                .subscribe(myObserver);

    }

    @Override
    protected void setListen() {
        mineBalanceWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataHelper.getIntergerSF(MineBalanceActivity.this, "userType") == 1){//代理商
                    startActivity(new Intent(MineBalanceActivity.this, WithdrawalActivity.class)
                    .putExtra("txMoney",makeMoney));
                }else {//商户
                    startActivity(new Intent(MineBalanceActivity.this, WithdrawalActivity.class)
                    .putExtra("txMoney",balance));
                }

            }
        });
    }

    @Override
    public void onLoadMore() {
        page = page + 1;

        myObserver = new MyObserver<BalanceRecordsBean>(MineBalanceActivity.this, false) {
            @Override
            public void onSuccess(BalanceRecordsBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getData() != null && result.getData().size() > 0) {
                    loadRecyclerView(result.getData());
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
                if (e != null) {
                    ToastUtils.show(MineBalanceActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(MineBalanceActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(MineBalanceActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().checkRecords(DataHelper.getIntergerSF(MineBalanceActivity.this, "userType") + "", page + "", "10", DataHelper.getStringSF(MineBalanceActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(MineBalanceActivity.this))
                .subscribe(myObserver);

    }

    private void loadRecyclerView(List<BalanceRecordsBean.DataBean> data) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(data);
    }
}
