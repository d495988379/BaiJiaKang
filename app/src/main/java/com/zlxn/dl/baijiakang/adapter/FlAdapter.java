package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.OneClassBean;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/20 16:31
 */
public class FlAdapter extends SimpleAdapter<OneClassBean> {

    public FlAdapter(Context context, int layoutResId, List<OneClassBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OneClassBean item) {
        viewHolder.getTextView(R.id.itemFlTv).setText(item.getCategoryName());
    }
}
