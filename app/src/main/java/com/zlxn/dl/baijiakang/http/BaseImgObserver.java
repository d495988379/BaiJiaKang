package com.zlxn.dl.baijiakang.http;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author DL
 * @name RetrofitTest
 * @class 返回基本参数判断（修改）
 * @time 2019/10/30 17:24
 */
public abstract class BaseImgObserver implements Observer<BaseImgResponse> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onNext(BaseImgResponse response) {
        //在这边对 基础数据 进行统一处理  举个例子：
        if (response.getCode().equals("200")) {
            Log.e("img_path",response.getDataxz());
            onSuccess(response.getDataxz());
        } else {
            onFailure(null, response.getCode() + "",response.getMessage());
           // onFailure(null, response.getCode() + "");
        }
    }


    @Override
    public void onError(Throwable e) {//服务器错误信息处理
        if (e != null){
            if(e instanceof ResultException){
                ResultException err = (ResultException) e;
                onFailure(null, err.getErrCode() + "","返回数据不匹配");
               // onFailure(null, err.getErrCode());
            }else {
              //  onFailure(e, RxExceptionUtil.exceptionHandler(e));
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


    public abstract void onSuccess(String result);

    public abstract void onFailure(Throwable e, String errorCode,String errorMsg);

}
