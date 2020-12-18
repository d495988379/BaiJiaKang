package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.CommodityListBean;
import com.zlxn.dl.baijiakang.http.Constans;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/20 16:31
 */
public class CommodityAdapter extends SimpleAdapter<CommodityListBean.RowsBean> {

    public CommodityAdapter(Context context, int layoutResId, List<CommodityListBean.RowsBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommodityListBean.RowsBean item) {
        Glide.with(context).load(Constans.PicUrl + item.getPic()).into(viewHolder.getImageView(R.id.commodityImg));
        viewHolder.getTextView(R.id.commodityName).setText(item.getProductName());
        viewHolder.getTextView(R.id.commodityMoney).setText(item.getPrice());
        viewHolder.getTextView(R.id.commodityQuan).setText(item.getCardRoll() + "");
        viewHolder.getTextView(R.id.commodityJy).setText("建议零售价：¥" + item.getOriginalPrice());
    }
}
