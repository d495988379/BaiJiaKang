package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.UserAdapter;
import com.zlxn.dl.baijiakang.adapter.UserAdapter1;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.DlsListBean;
import com.zlxn.dl.baijiakang.bean.UserListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

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
 * @time 2020/10/16 15:03
 */
public class UserListActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.userRv)
    RecyclerView userRv;
    private MyObserver<List<DlsListBean>> myDslObserver;
    private MyObserver<List<UserListBean>> myObserver;
    private UserAdapter adapter;
    private UserAdapter1 adapter1;

    @Override
    protected void initView() {
        setContentView(R.layout.act_user);
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

        userRv.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getStringExtra("location").equals("dls")){
            myDslObserver = new MyObserver<List<DlsListBean>>(UserListActivity.this,true) {
                @Override
                public void onSuccess(List<DlsListBean> result) {
                    adapter = new UserAdapter(UserListActivity.this,R.layout.item_user,result);
                    userRv.setAdapter(adapter);
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    if (e != null) {
                        ToastUtils.show(UserListActivity.this, errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(UserListActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(UserListActivity.this, errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().dlsDetails(getIntent().getStringExtra("type"), DataHelper.getStringSF(UserListActivity.this,"token"))
                    .compose(RxHelper.observableIO2Main(UserListActivity.this))
                    .subscribe(myDslObserver);
        }else {
            myObserver = new MyObserver<List<UserListBean>>(UserListActivity.this,true) {
                @Override
                public void onSuccess(List<UserListBean> result) {
                    adapter1 = new UserAdapter1(UserListActivity.this,R.layout.item_user,result);
                    userRv.setAdapter(adapter1);
                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    if (e != null) {
                        ToastUtils.show(UserListActivity.this, errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(UserListActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(UserListActivity.this, errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().userDetails(getIntent().getStringExtra("type"), DataHelper.getStringSF(UserListActivity.this,"token"))
                    .compose(RxHelper.observableIO2Main(UserListActivity.this))
                    .subscribe(myObserver);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null){
            myObserver.cancleRequest();
        }
        if (myDslObserver != null){
            myDslObserver.cancleRequest();
        }
    }
}
