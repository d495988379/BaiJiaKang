package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;
import com.zlxn.dl.baijiakang.R;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/20 16:51
 */
public class MarqueeViewAdapter extends XMarqueeViewAdapter<String> {

    private Context mContext;

    public MarqueeViewAdapter(List<String> datas, Context context) {
        super(datas);
        mContext = context;
    }


    @Override
    public View onCreateView(XMarqueeView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marqueeview, null);
    }

    @Override
    public void onBindView(View parent, View view, int position) {
        TextView tvOne = (TextView) view.findViewById(R.id.marquee_tv_one);
        tvOne.setText(mDatas.get(position));
    }
}
