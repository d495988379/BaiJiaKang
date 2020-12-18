/*
 * Copyright (c) 2020 - present. All rights reserved.
 *
 * Author : Xianxiang.Hu
 * E-mail : huxianxiang@gmail.com
 * Version: v1.0.0
 * Date   : 2020/01/14 12:55
 * Desc   :
 */

package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.bean.ChildBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<ChildBean> mData;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private int columnCount = 5;

    public TopicAdapter(Context context, List<ChildBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, final int position) {
        final ChildBean item = mData.get(position);
        if (item == null) {
            return;
        }
        holder.itemChildName.setText(item.getCategoryName());
        Glide.with(mContext).load(Constans.PicUrl + item.getPic()).into(holder.itemChildImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onTopicItemClick(item);
                }
            }
        });

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels; //屏幕宽度
        params.width = screenWidth / columnCount;
        holder.itemView.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {

        public TextView itemChildName;
        public ImageView itemChildImg;

        public TopicViewHolder(View view) {
            super(view);
            itemChildName = view.findViewById(R.id.itemChildName);
            itemChildImg = view.findViewById(R.id.itemChildImg);
        }
    }

    public interface OnItemClickListener {

        public void onTopicItemClick(ChildBean position);

    }

}
