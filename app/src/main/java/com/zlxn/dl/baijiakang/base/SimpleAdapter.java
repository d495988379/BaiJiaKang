package com.zlxn.dl.baijiakang.base;

import android.content.Context;

import java.util.List;

/**
 * Created by Motee
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {


    public SimpleAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }

    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

}
