package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.jess.arms.utils.DataHelper;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.AddressListActivity;
import com.zlxn.dl.baijiakang.activity.ConfirmOrderActivity;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.AddressBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/19 17:16
 */
public class AddressAdapter extends SimpleAdapter<AddressBean.AddressListBean> {

    public AddressAdapter(Context context, int layoutResId, List<AddressBean.AddressListBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, AddressBean.AddressListBean item) {
        viewHolder.getTextView(R.id.addressName).setText("收货人：" + item.getUserName());
        viewHolder.getTextView(R.id.addressAddress).setText("收货地址：" + item.getArea() + item.getDetailArea());
        viewHolder.getTextView(R.id.addressPhone).setText(item.getPhone());

        if (item.getConfirm() == 1){
            viewHolder.getImageView(R.id.iv_select).setImageResource(R.mipmap.ic_select_img);
        }else {
            viewHolder.getImageView(R.id.iv_select).setImageResource(R.mipmap.ic_unselect_img);
        }

        viewHolder.getImageView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getConfirm() != 1){
                    RetrofitUtils.getApiUrl().defaultAddress(DataHelper.getIntergerSF(context, "userType") + "",item.getId() + "", DataHelper.getStringSF(context,"token"))
                            .compose(RxHelper.observableIO2Main(context))
                            .subscribe(new MyObserver<String>(context,true) {
                                @Override
                                public void onSuccess(String result) {
                                    for (int i = 0; i < datas.size(); i++) {
                                        datas.get(i).setConfirm(0);
                                    }
                                    item.setConfirm(1);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                    if (e != null) {
                                        ToastUtils.show(context, errorCode);
                                    } else {
                                        if (errorCode.equals("401")) {
                                            Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else {
                                            ToastUtils.show(context,errorMsg);
                                        }
                                    }
                                }
                            });
                }

            }
        });


    }
}
