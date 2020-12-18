package com.zlxn.dl.baijiakang.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author DL
 * @name BossApp
 * @class describe
 * @time 2020/5/13 10:39
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            BaseResponse result = gson.fromJson(response, BaseResponse.class);
            String code = result.getCode() + "";
            if (code.equals("200")) {
                return gson.fromJson(response, type);
            } else {
                Log.d("HttpManager", "返回err==：" + response);
                HttpErrResult errResponse = gson.fromJson(response, HttpErrResult.class);
                throw new ResultException(errResponse.getMessage(), Integer.parseInt(code),errResponse.getMessage());
            }
        } finally {
            value.close();
        }
    }
}
