package com.zlxn.dl.baijiakang.http;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @author DL
 * @name RetrofitTest
 * @class describe
 * @time 2019/10/30 17:28
 */
public class RxExceptionUtil {

    public static String exceptionHandler(Throwable e){
        String errorMsg = "Unknown Error";
        if (e instanceof UnknownHostException) {
            errorMsg = "Network Unavailable";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "Network Timeout";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
        } else if (e instanceof ParseException || e instanceof JSONException
                || e instanceof JSONException) {
            errorMsg = "Parsing Error";
        }
        return errorMsg;
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "Server processing request error";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "Server was unable to process the request";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "Request redirected to another page";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
