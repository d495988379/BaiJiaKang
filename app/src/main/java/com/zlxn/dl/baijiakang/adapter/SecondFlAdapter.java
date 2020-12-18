package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

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
public class SecondFlAdapter extends SimpleAdapter<OneClassBean> {


    public SecondFlAdapter(Context context, int layoutResId, List<OneClassBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OneClassBean item) {
        viewHolder.getTextView(R.id.itemFlTv).setText(item.getCategoryName());
        if (item.isSelect()){
            viewHolder.getTextView(R.id.itemFlTv).setTextSize(17);
            viewHolder.getTextView(R.id.itemFlTv).setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.getTextView(R.id.itemFlTv).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            viewHolder.getTextView(R.id.itemFlTv).setTextSize(16);
            viewHolder.getTextView(R.id.itemFlTv).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.getTextView(R.id.itemFlTv).setTextColor(Color.parseColor("#CAE3DD"));
        }
    }
}
