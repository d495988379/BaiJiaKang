package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class AgentUserAdapter extends SimpleAdapter<String> {

    public AgentUserAdapter(Context context, int layoutResId, List<String> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item) {

    }
}
