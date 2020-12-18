package com.zlxn.dl.baijiakang.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemLongClickListener, View.OnLongClickListener {


    private SparseArray<View> views;

    private BaseAdapter.OnItemClickListener mOnItemClickListener ;
    BaseAdapter.OnLongItemClicListner onLongItemClicListner;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener, BaseAdapter.OnLongItemClicListner onLongItemClicListner){
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        this.mOnItemClickListener =onItemClickListener;
        this.onLongItemClicListner = onLongItemClicListner;
        this.views = new SparseArray<View>();
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getSDV(int viewId) {
        return retrieveView(viewId);
    }

    public View getView(int viewId) {
        return retrieveView(viewId);
    }






    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }


    @Override
    public boolean onLongClick(View view) {
        if (onLongItemClicListner != null){
            onLongItemClicListner.onItemClick(view,getLayoutPosition());
        }
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (onLongItemClicListner != null){
            onLongItemClicListner.onItemClick(view,getLayoutPosition());
        }
        return false;
    }
}
