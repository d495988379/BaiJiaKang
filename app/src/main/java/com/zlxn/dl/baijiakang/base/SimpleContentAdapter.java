package com.zlxn.dl.baijiakang.base;

import android.content.Context;

import java.util.List;

/**
 * Created by Motee
 */
public abstract class SimpleContentAdapter<T> extends BaseContentAdapter<T,BaseContentViewHolder> {


    public SimpleContentAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }

    public SimpleContentAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

}
