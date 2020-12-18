package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.BasicListBean;
import com.zlxn.dl.baijiakang.utils.DateUtil;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class AgentMxAdapter extends SimpleAdapter<BasicListBean.RowsBean> {

    public AgentMxAdapter(Context context, int layoutResId, List<BasicListBean.RowsBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BasicListBean.RowsBean item) {
        viewHolder.getTextView(R.id.itemAgentTime).setText(DateUtil.getDateToString(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        viewHolder.getTextView(R.id.itemAgentName).setText(item.getByUserName() != null ? item.getByUserName() : "");
        viewHolder.getTextView(R.id.itemAgentMoney).setText(item.getPayMoney() != null? item.getPayMoney():"");
    }
}
