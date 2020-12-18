package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.OrderDetailsBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/16 15:45
 */
public class OrderDetailsAdapter extends SimpleAdapter<OrderDetailsBean.OrderDetailBean> {

    public OrderDetailsAdapter(Context context, int layoutResId, List<OrderDetailsBean.OrderDetailBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OrderDetailsBean.OrderDetailBean item) {
        viewHolder.getTextView(R.id.itemCount).setText("共" + item.getNumber() + "件");
        viewHolder.getTextView(R.id.carName).setText(item.getProductName());
        viewHolder.getTextView(R.id.carMoney).setText(item.getPrice());
        viewHolder.getTextView(R.id.carQuan).setText(item.getTicket());
        viewHolder.getTextView(R.id.carGg).setText("规格：" + item.getSpecificationName());
        Glide.with(context).load(Constans.PicUrl + item.getPic()).into(viewHolder.getImageView(R.id.iv_img));

    }
}
