package com.zlxn.dl.baijiakang.http;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author DL
 * @name JinHuDao
 * @class describe
 * @time 2020/12/7 15:40
 */
public class HandleErrorInterceptor extends ResponseBodyInterceptor{

    @Override
    Response intercept(@NotNull Response response, String url, String body){
       // Log.w("hao", "拦截器拦到的返回数据：" + body);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            if (jsonObject.optString("code").equals("200")) {
                Log.w("hao", "返回失败=============");
                if (response.body() != null) {
                    try {
                        String json = response.body().string().replace("\"data\":[]", "\"data\":{}");
                        ResponseBody boy = ResponseBody.create(response.body().contentType(), json);
                        return response.newBuilder().body(boy).build();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
           /* }else {
                if (response.body() != null) {
                    try {
                        String json = response.body().string().replace("\"data\":[]", "\"data\":{}");
                        ResponseBody boy = ResponseBody.create(response.body().contentType(), json);
                        return response.newBuilder().body(boy).build();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }*/
            }
        }
        return response;
    }

}
