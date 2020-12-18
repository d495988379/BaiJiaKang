package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.DlsListBean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class UserAdapter extends SimpleAdapter<DlsListBean> {

    public UserAdapter(Context context, int layoutResId, List<DlsListBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DlsListBean item) {
        viewHolder.getTextView(R.id.itemUserName).setText(item.getAgentsName() != null ? item.getAgentsName() : "");
        viewHolder.getTextView(R.id.itemAddress).setText(item.getAreaName() != null ? item.getAreaName() : "");
    }
}
