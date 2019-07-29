package com.vince.divineweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc : 处理Http请求
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String addr, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(addr).build();
        client.newCall(request).enqueue(callback);
    }
}
