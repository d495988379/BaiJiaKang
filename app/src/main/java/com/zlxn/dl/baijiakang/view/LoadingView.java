package com.zlxn.dl.baijiakang.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zlxn.dl.baijiakang.R;


/**
 * @author DL
 * @name AutoFinance
 * @class describe
 * @time 2020/9/15 13:40
 */
public class LoadingView extends ImageView implements PictureIndeterminate {

    private float mRotateDegrees = 0f;
    private int mFrameTime = 0;
    private boolean mNeedToUpdateView = false;
    private Runnable mUpdateViewRunnable = null;


    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setImageResource(R.mipmap.loading);
        mFrameTime = 1000 / 12;
        mUpdateViewRunnable = new Runnable() {
            @Override
            public void run() {
                mRotateDegrees += 30f;
                if (mRotateDegrees < 360){
                    mRotateDegrees = mRotateDegrees;
                }else {
                    mRotateDegrees = mRotateDegrees - 360;
                }
                invalidate();
                if (mNeedToUpdateView){
                    postDelayed(this, mFrameTime);
                }
            }
        };
    }

    @Override
    public void setAnimationSpeed(float scale) {
        mFrameTime = (int) (1000f / 12f / scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mRotateDegrees, (float) getWidth() / 2, (float) getHeight() / 2);
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedToUpdateView = true;
        post(mUpdateViewRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        mNeedToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
