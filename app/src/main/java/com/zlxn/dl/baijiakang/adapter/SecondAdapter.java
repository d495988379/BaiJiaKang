package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.ChildBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/21 16:34
 */
public class SecondAdapter extends SimpleAdapter<ChildBean> {

    public SecondAdapter(Context context, int layoutResId, List<ChildBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ChildBean item) {
        viewHolder.getTextView(R.id.itemChildName).setText(item.getCategoryName());
        Glide.with(context).load(Constans.PicUrl + item.getPic()).into(viewHolder.getImageView(R.id.itemChildImg));
    }
}
