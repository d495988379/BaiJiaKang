package com.zlxn.dl.baijiakang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.utils.DimensionConvert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/24 15:18
 */
public class PageIndicatorView extends LinearLayout {

    private Context mContext = null;
    private int kuanSize = 40; // 指示器的大小（dp）
    private int gaoSize = 3; // 指示器的大小（dp）
    private int margins = 0; // 指示器间距（dp）
    private List<View> indicatorViews = null; // 存放指示器

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        kuanSize = DimensionConvert.dip2px(context, kuanSize);
        gaoSize = DimensionConvert.dip2px(context, gaoSize);
        margins = DimensionConvert.dip2px(context, margins);
    }

    /**
     * 初始化指示器，默认选中第一页
     *
     * @param count 指示器数量，即页数
     */
    public void initIndicator(int count) {

        if (indicatorViews == null) {
            indicatorViews = new ArrayList<>();
        } else {
            indicatorViews.clear();
            removeAllViews();
        }
        View view;
        LayoutParams params = new LayoutParams(kuanSize, gaoSize);
        params.setMargins(margins, margins, margins, margins);
        for (int i = 0; i < count; i++) {
            view = new View(mContext);
            view.setBackgroundResource(R.drawable.shape_unindicator);
            addView(view, params);
            indicatorViews.add(view);
        }
        if (indicatorViews.size() > 0) {
            indicatorViews.get(0).setBackgroundResource(R.drawable.shape_indicator);
        }
    }

    /**
     * 设置选中页
     *
     * @param selected 页下标，从0开始
     */
    public void setSelectedPage(int selected) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == selected) {
                indicatorViews.get(i).setBackgroundResource(R.drawable.shape_indicator);
            } else {
                indicatorViews.get(i).setBackgroundResource(R.drawable.shape_unindicator);
            }
        }
    }

}
