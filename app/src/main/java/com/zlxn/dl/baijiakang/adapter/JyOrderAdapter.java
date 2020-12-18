package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseEmptyViewHolder;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.EmptyAdapter;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.JyOrderListBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/15 10:53
 */
public class JyOrderAdapter extends EmptyAdapter<JyOrderListBean.DataBean> {


    public JyOrderAdapter(Context context, int layoutResId, List<JyOrderListBean.DataBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseEmptyViewHolder viewHolder, JyOrderListBean.DataBean item) {
        viewHolder.getTextView(R.id.itemJyName).setText(item.getGasname());
        viewHolder.getTextView(R.id.yhOrderState).setText(item.getOrderstatusname());
        viewHolder.getTextView(R.id.itemJyNum).setText("加油单号：" + item.getOrderid());
        viewHolder.getTextView(R.id.itemJyYq).setText("油枪：" + item.getGunno());
        viewHolder.getTextView(R.id.itemJyYp).setText("油品：" + item.getOilno());
        viewHolder.getTextView(R.id.itemJyCount).setText("升数：" + item.getLitre());
        viewHolder.getTextView(R.id.itemJyTime).setText(item.getOrderdt());
        viewHolder.getTextView(R.id.itemJyMoney).setText("¥ " + item.getAmountpay());

        BigDecimal bg = new BigDecimal((item.getAmountgun() - item.getAmountpay()));
        double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        viewHolder.getTextView(R.id.itemJyYh).setText("优惠价" + money);

    }
}
