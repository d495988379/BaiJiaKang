package com.zlxn.dl.baijiakang.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;


/**
 *  Android实现获取验证码的倒计时功能
 */
public class CountDownTimerTvUtils extends CountDownTimer {
    //使用
    //CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mButton, 60000, 1000);

    //    mCountDownTimerUtils.start();
    private TextView mTextView;
    private EditText identifyEt;

    public CountDownTimerTvUtils(long millisInFuture, long countDownInterval, TextView mTextView, EditText identifyEt) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
        this.identifyEt = identifyEt;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + " s");//设置倒计时时间

        //获取按钮上面的字
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
        //将倒计时时间设置为红色
        spannableString.setSpan(span,0,2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

    }

    @Override
    public void onFinish() {
//        mTextView.setBackgroundResource(R.mipmap.img_code_back);
        mTextView.setText("Send");
        mTextView.setEnabled(true);
        identifyEt.setFocusable(true);
        identifyEt.setFocusableInTouchMode(true);
        identifyEt.requestFocus();
        mTextView.setClickable(true);
//        mTextView.setBackgroundResource();
    }
}
