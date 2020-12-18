package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.ArmsUtils;
import com.willy.ratingbar.ScaleRatingBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseEmptyViewHolder;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.EmptyAdapter;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.PjListBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class PjAdapter extends EmptyAdapter<PjListBean.DataBean> {

    public PjAdapter(Context context, int layoutResId, List<PjListBean.DataBean> datas) {
        super(context, layoutResId, datas);
    }


    @Override
    protected void convert(BaseEmptyViewHolder viewHolder, PjListBean.DataBean item) {
        viewHolder.getTextView(R.id.itemPjContent).setText(item.getEvaluation());

        ScaleRatingBar ratingBar = (ScaleRatingBar) viewHolder.getView(R.id.simpleRatingBar);

        ratingBar.setRating(item.getStar());
        if (item.getUserId() == 0) {//代理商
            viewHolder.getTextView(R.id.itemPjName).setText(item.getStName() != null ? item.getStName() : "");
            if (item.getStpic() != null) {
                Glide.with(context).load(Constans.PicUrl + item.getStpic()).into(viewHolder.getImageView(R.id.itemPjHead));
            }else {
                Glide.with(context).load(R.mipmap.ic_default_head).into(viewHolder.getImageView(R.id.itemPjHead));
            }
        } else {
            viewHolder.getTextView(R.id.itemPjName).setText(item.getUsName() != null ? item.getUsName() : "");
            if (item.getUspic() != null) {
                Glide.with(context).load(Constans.PicUrl + item.getUspic()).into(viewHolder.getImageView(R.id.itemPjHead));
            }else {
                Glide.with(context).load(R.mipmap.ic_default_head).into(viewHolder.getImageView(R.id.itemPjHead));
            }
        }

        if (item.getPic() != null) {
            String[] split = item.getPic().split(",");
            if (split.length == 1) {
                Glide.with(context).load(Constans.PicUrl + split[0]).into(viewHolder.getImageView(R.id.itemPjImg1));
            } else if (split.length == 2) {
                Glide.with(context).load(Constans.PicUrl + split[0]).into(viewHolder.getImageView(R.id.itemPjImg1));
                Glide.with(context).load(Constans.PicUrl + split[1]).into(viewHolder.getImageView(R.id.itemPjImg2));
            } else if (split.length == 3) {
                Glide.with(context).load(Constans.PicUrl + split[0]).into(viewHolder.getImageView(R.id.itemPjImg1));
                Glide.with(context).load(Constans.PicUrl + split[1]).into(viewHolder.getImageView(R.id.itemPjImg2));
                Glide.with(context).load(Constans.PicUrl + split[2]).into(viewHolder.getImageView(R.id.itemPjImg3));
            }
        }
    }
}
