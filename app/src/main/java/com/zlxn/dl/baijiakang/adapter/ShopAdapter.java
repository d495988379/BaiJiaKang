package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.DataHelper;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.activity.ShoppingCarActivity;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.http.Constans;
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
public class ShopAdapter extends SimpleAdapter<ShopCarBean.DataBean> {

    private int count;

    private OnSelectClickListener mOnSelectClickListener = null;
    private OnAddClickListener mOnAddClickListener = null;
    private OnJianClickListener mOnJianClickListener = null;

    public interface OnSelectClickListener {
        void onSelectClick(View view, int position);
    }

    public interface OnAddClickListener {
        void onAddClick(View view, int position);
    }

    public interface OnJianClickListener {
        void onJianClick(View view, int position);
    }

    public void setOnSelectClickListener(OnSelectClickListener listener) {
        this.mOnSelectClickListener = listener;
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.mOnAddClickListener = listener;
    }

    public void setOnJianClickListener(OnJianClickListener listener) {
        this.mOnJianClickListener = listener;
    }


    public ShopAdapter(Context context, int layoutResId, List<ShopCarBean.DataBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ShopCarBean.DataBean item) {
        Glide.with(context).load(Constans.PicUrl + item.getPic()).into(viewHolder.getImageView(R.id.iv_img));
        viewHolder.getTextView(R.id.carMoney).setText(item.getPrice());
        viewHolder.getTextView(R.id.carQuan).setText(item.getTicket() + "");
        viewHolder.getTextView(R.id.carName).setText(item.getProductName());
        viewHolder.getTextView(R.id.itemCount).setText(item.getNumber() + "");
        viewHolder.getTextView(R.id.carGg).setText("规格：" + item.getSpecification());
        if (item.isSelect()) {
            viewHolder.getImageView(R.id.iv_select).setImageResource(R.mipmap.ic_select_img);
        } else {
            viewHolder.getImageView(R.id.iv_select).setImageResource(R.mipmap.ic_unselect_img);
        }

        viewHolder.getImageView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnSelectClickListener != null) {
                    mOnSelectClickListener.onSelectClick(view, viewHolder.getLayoutPosition());
                }
            }
        });


        viewHolder.getImageView(R.id.itemJianImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnJianClickListener != null) {
                    mOnJianClickListener.onJianClick(view, viewHolder.getLayoutPosition());
                }


              /*  count = Integer.parseInt(viewHolder.getTextView(R.id.itemCount).getText().toString());
                if (count > 1) {
                    count--;
                    RetrofitUtils.getApiUrl().JianComm(item.getId() + "", DataHelper.getStringSF(context, "token"))
                            .compose(RxHelper.observableIO2Main(context))
                            .subscribe(new MyObserver<String>(context,true) {
                                @Override
                                public void onSuccess(String result) {
                                    item.setNumber(count);
                                    viewHolder.getTextView(R.id.itemCount).setText(count + "");
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
                                            ToastUtils.show(context, errorMsg);
                                        }
                                    }
                                }
                            });
                }*/

            }
        });


        viewHolder.getImageView(R.id.itemAddImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnAddClickListener != null) {
                    mOnAddClickListener.onAddClick(view, viewHolder.getLayoutPosition());
                }
             /*   count = Integer.parseInt(viewHolder.getTextView(R.id.itemCount).getText().toString());
                count++;

                RetrofitUtils.getApiUrl().addComm(item.getId() + "", DataHelper.getStringSF(context, "token"))
                        .compose(RxHelper.observableIO2Main(context))
                        .subscribe(new MyObserver<String>(context,true) {
                            @Override
                            public void onSuccess(String result) {
                                item.setNumber(count);
                                viewHolder.getTextView(R.id.itemCount).setText(count + "");
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
                                        ToastUtils.show(context, errorMsg);
                                    }
                                }
                            }
                        });*/
            }
        });


    }
}
