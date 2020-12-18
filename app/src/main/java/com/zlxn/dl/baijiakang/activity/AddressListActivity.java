package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.AddressAdapter;
import com.zlxn.dl.baijiakang.adapter.ShopAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.bean.AddressBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/11 16:22
 */
public class AddressListActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.addressRv)
    SwipeRecyclerView addressRv;
    @BindView(R.id.addressListBtn)
    Button addressListBtn;
    private MyObserver<AddressBean> myObserver;
    private MyObserver<String> myDeleteObserver;
    private AddressAdapter adapter;
    private List<AddressBean.AddressListBean> addressList;
    private String location;

    @Override
    protected void initView() {
        setContentView(R.layout.act_address_list);
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

        initSlide();

        location = getIntent().getStringExtra("location");

        myObserver = new MyObserver<AddressBean>(AddressListActivity.this,true) {
            @Override
            public void onSuccess(AddressBean result) {
                if (result.getAddressList() != null && result.getAddressList().size() > 0){
                    addressList = result.getAddressList();
                    adapter = new AddressAdapter(AddressListActivity.this, R.layout.item_address, result.getAddressList());
                    addressRv.setAdapter(adapter);

                    adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (location.equals("order")){
                                Intent intent = new Intent();
                                intent.putExtra("userName",result.getAddressList().get(position).getUserName());
                                intent.putExtra("phone",result.getAddressList().get(position).getPhone());
                                intent.putExtra("addressId",result.getAddressList().get(position).getId());
                                intent.putExtra("address",result.getAddressList().get(position).getArea() + result.getAddressList().get(position).getDetailArea());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(AddressListActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(AddressListActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(AddressListActivity.this,errorMsg);
                    }
                }

            }
        };

        RetrofitUtils.getApiUrl().addressList(DataHelper.getIntergerSF(AddressListActivity.this, "userType") + "",DataHelper.getStringSF(AddressListActivity.this,"token"))
                .compose(RxHelper.observableIO2Main(this))
                .subscribe(myObserver);



    }

    private void initSlide() {
        addressRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider_address));
        addressRv.addItemDecoration(divider);


        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(AddressListActivity.this);
                deleteItem.setText("删除")
                        .setBackgroundColor(getResources().getColor(R.color.red))
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(14) // 文字大小。
                        .setWidth(150)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                rightMenu.addMenuItem(deleteItem);
            }
        };
        addressRv.setSwipeMenuCreator(mSwipeMenuCreator);



        OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                myDeleteObserver = new MyObserver<String>(AddressListActivity.this,true) {
                    @Override
                    public void onSuccess(String result) {
                        menuBridge.closeMenu();
                        addressList.remove(adapterPosition);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(AddressListActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(AddressListActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(AddressListActivity.this,errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().deleteAddress(DataHelper.getIntergerSF(AddressListActivity.this, "userType") + "",addressList.get(adapterPosition).getId() + "",DataHelper.getStringSF(AddressListActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(AddressListActivity.this))
                        .subscribe(myDeleteObserver);
            }
        };

        // 菜单点击监听。
        addressRv.setOnItemMenuClickListener(mMenuItemClickListener);


    }

    @Override
    protected void setListen() {
        addressListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddressListActivity.this,AddressAddActivity.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1){
                myObserver = new MyObserver<AddressBean>(AddressListActivity.this,true) {
                    @Override
                    public void onSuccess(AddressBean result) {
                        if (result.getAddressList() != null && result.getAddressList().size() > 0){
                            addressList = result.getAddressList();
                            adapter = new AddressAdapter(AddressListActivity.this, R.layout.item_address, result.getAddressList());
                            addressRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    if (location.equals("order")){
                                        Intent intent = new Intent();
                                        intent.putExtra("userName",result.getAddressList().get(position).getUserName());
                                        intent.putExtra("phone",result.getAddressList().get(position).getPhone());
                                        intent.putExtra("addressId",result.getAddressList().get(position).getId());
                                        intent.putExtra("address",result.getAddressList().get(position).getArea() + result.getAddressList().get(position).getDetailArea());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(AddressListActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(AddressListActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(AddressListActivity.this,errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().addressList(DataHelper.getIntergerSF(AddressListActivity.this, "userType") + "",DataHelper.getStringSF(AddressListActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(this))
                        .subscribe(myObserver);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myObserver.cancleRequest();
        if (myDeleteObserver != null){
            myDeleteObserver.cancleRequest();
        }
    }
}
