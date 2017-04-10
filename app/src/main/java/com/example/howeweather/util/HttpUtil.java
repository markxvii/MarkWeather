package com.example.howeweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by howeyin on 2017/2/12.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
