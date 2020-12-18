package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.JyListBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/12/1 17:19
 */
public class JyAdapter extends SimpleAdapter<JyListBean.DataBean> {

    private BigDecimal bg;
    private String type;

    public JyAdapter(Context context, int layoutResId, List<JyListBean.DataBean> datas, String type) {
        super(context, layoutResId, datas);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, JyListBean.DataBean item) {
        viewHolder.getTextView(R.id.oldMoney).setPaintFlags(viewHolder.getTextView(R.id.oldMoney).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(item.getGaslogosmall()).into(viewHolder.getImageView(R.id.itemJyImg));
        viewHolder.getTextView(R.id.itemJyName).setText(item.getGasName());
        viewHolder.getTextView(R.id.itemJyAddress).setText(item.getAddress());



        bg = new BigDecimal(Double.parseDouble(item.getDistance()) / 1000);
        double distance = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        viewHolder.getTextView(R.id.itemJyJl).setText(distance + "km");

        for (int i = 0; i < item.getOilPriceList().size(); i++) {
            if (type.equals(item.getOilPriceList().get(i).getOilName())) {
                viewHolder.getTextView(R.id.yhMoney).setText(item.getOilPriceList().get(i).getPriceYfq());
                viewHolder.getTextView(R.id.oldMoney).setText(item.getOilPriceList().get(i).getPriceGun());
                double moneyGun = Double.parseDouble(item.getOilPriceList().get(i).getPriceGun());
                double moneyYfq = Double.parseDouble(item.getOilPriceList().get(i).getPriceYfq());
                BigDecimal bg = new BigDecimal((moneyGun - moneyYfq));
                double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                viewHolder.getTextView(R.id.jyYh).setText("省" + money + "元");
                break;
            }
        }

    }
}
