package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseContentViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleContentAdapter;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 10:45
 */
public class OrderContentAdapter extends SimpleContentAdapter<OrderListBean.DataBean.OrderDetailBean> {

    public OrderContentAdapter(Context context, int layoutResId, List<OrderListBean.DataBean.OrderDetailBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseContentViewHolder viewHolder, OrderListBean.DataBean.OrderDetailBean item) {
        viewHolder.getTextView(R.id.itemCount).setText("共" + item.getNumber() + "件");
        viewHolder.getTextView(R.id.carName).setText(item.getProductName());
        viewHolder.getTextView(R.id.carMoney).setText(item.getPrice());
        viewHolder.getTextView(R.id.carQuan).setText(item.getTicket());
        viewHolder.getTextView(R.id.carGg).setText("规格：" + item.getSpecificationName());
        Glide.with(context).load(Constans.PicUrl + item.getPic()).into(viewHolder.getImageView(R.id.iv_img));
    }
}
