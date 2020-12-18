package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.BalanceRecordsBean;
import com.zlxn.dl.baijiakang.utils.DateUtil;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class BalanceAdapter extends SimpleAdapter<BalanceRecordsBean.DataBean> {

    public BalanceAdapter(Context context, int layoutResId, List<BalanceRecordsBean.DataBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BalanceRecordsBean.DataBean item) {
        viewHolder.getTextView(R.id.itemBalanceName).setText(item.getByUserName());
        viewHolder.getTextView(R.id.itemBalanceMoney).setText(item.getPayMoney() + "");
        viewHolder.getTextView(R.id.itemBalanceTime).setText(DateUtil.getDateToString(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        viewHolder.getTextView(R.id.itemBalanceType).setText(item.getType() != null ? item.getType() : "");
    }
}
