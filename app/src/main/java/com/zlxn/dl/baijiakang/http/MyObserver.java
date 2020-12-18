package com.zlxn.dl.baijiakang.http;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.zlxn.dl.baijiakang.view.CustomProgressDialog;

import io.reactivex.disposables.Disposable;

/**
 * @author DL
 * @name RetrofitTest
 * @class 网络请求效果框
 * @time 2019/10/30 18:02
 */
public abstract class MyObserver<T> extends BaseObserver<T> {

    private boolean mShowDialog;
    private CustomProgressDialog dialog;
    private Context mContext;
    private Disposable d;

    public MyObserver(Context context, Boolean showDialog) {
        mContext = context;
        mShowDialog = showDialog;
    }

    public MyObserver(Context context) {
        this(context, true);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (!isConnected(mContext)) {
            if (d.isDisposed()) {
                d.dispose();
            }
        } else {
            if (dialog == null && mShowDialog == true) {
                dialog = new CustomProgressDialog(mContext, "Loading...",d);
                dialog.show();
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        if (dialog != null && mShowDialog == true)
            dialog.dismiss();
        dialog = null;
        super.onError(e);
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        if (dialog != null && mShowDialog == true)
            dialog.dismiss();
        dialog = null;
        super.onComplete();


    }


    /**
     * 取消订阅
     */
    public void cancleRequest() {
        if (d != null && d.isDisposed()) {
            d.dispose();
        }

        if (dialog != null && mShowDialog == true)
            dialog.dismiss();
        dialog = null;

    }

    /**
     * 是否有网络连接，不管是wifi还是数据流量
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }

}
