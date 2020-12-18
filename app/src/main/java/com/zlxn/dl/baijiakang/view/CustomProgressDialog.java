package com.zlxn.dl.baijiakang.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.zlxn.dl.baijiakang.R;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author DL
 * @name RetrofitTest
 * @class describe
 * @time 2019/11/1 16:15
 */
public class CustomProgressDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext = new WeakReference<>(null);
    private volatile static CustomProgressDialog sDialog;
    private String mText;
    private Disposable mDisposable;

    public CustomProgressDialog(Context context, String message, Disposable disposable) {
        super(context, R.style.CustomProgressDialog);
        mText = message;
        mContext = new WeakReference<>(context);
        mDisposable = disposable;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_progress, null);

        setCanceledOnTouchOutside(false);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);
        setOnCancelListener(this);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
      //  Context context = mContext.get();
        dialog.dismiss();

        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

    }

    public static synchronized void showLoading(Context context,Disposable disposable) {
        showLoading(context, "Loading...",0,disposable);
    }

    public static synchronized void showLoading(Context context, String message, int img,Disposable disposable) {
        showLoading(context, message, true,img,disposable);
    }

    public static synchronized void showLoading(Context context, String message, boolean cancelable, int img,Disposable disposable) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }

        if (context == null || !(context instanceof Activity)) {
            return;
        }
        sDialog = new CustomProgressDialog(context, message,disposable);
        sDialog.setCancelable(cancelable);

        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    public static synchronized void stopLoading() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }

}
