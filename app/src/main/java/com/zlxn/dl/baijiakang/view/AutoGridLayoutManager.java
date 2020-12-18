package com.zlxn.dl.baijiakang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/24 15:21
 */
public class AutoGridLayoutManager extends GridLayoutManager {

    private int measuredWidth = 0;
    private int measuredHeight = 0;

    public AutoGridLayoutManager(Context context, AttributeSet attrs,
                                 int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AutoGridLayoutManager(Context context, int spanCount,
                                 int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler,
                          RecyclerView.State state, int widthSpec, int heightSpec) {
        //获取count判断，必须要有
        int count = state.getItemCount();
        if (count > 0) {
            if (measuredHeight <= 0) {
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    measuredHeight = view.getMeasuredHeight() * getSpanCount();
                }
            }
            setMeasuredDimension(measuredWidth, measuredHeight);
        }else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }


    }


}
