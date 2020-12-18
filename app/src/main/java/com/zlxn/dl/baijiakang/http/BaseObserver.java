package com.zlxn.dl.baijiakang.http;


import android.util.Log;

import com.google.gson.JsonSyntaxException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author DL
 * @name RetrofitTest
 * @class 返回基本参数判断（修改）
 * @time 2019/10/30 17:24
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onNext(BaseResponse<T> response) {
        //在这边对 基础数据 进行统一处理  举个例子：
        if (response.getCode() == 200) {
            onSuccess(response.getData());
        } else {
            onFailure(null, response.getCode() + "",response.getMessage());
        }
    }


    @Override
    public void onError(Throwable e) {//服务器错误信息处理
        if (e != null){
            if(e instanceof ResultException){
                ResultException err = (ResultException) e;
                onFailure(null, err.getErrCode() + "","返回数据不匹配");
            }else {
                onFailure(e, RxExceptionUtil.exceptionHandler(e),"");
            }
        }


    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }


    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable e, String errorCode,String errorMsg);

}
