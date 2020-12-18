package com.zlxn.dl.baijiakang.base;

import android.content.Context;

import java.util.List;

/**
 * Created by Motee
 */
public abstract class EmptyAdapter<T> extends BaseEmptyAdapter<T,BaseEmptyViewHolder> {


    public EmptyAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }

    public EmptyAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

}
