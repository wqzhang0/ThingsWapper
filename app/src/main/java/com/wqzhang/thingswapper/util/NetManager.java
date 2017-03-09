package com.wqzhang.thingswapper.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


/**
 * Created by wqzhang on 17-3-9.
 */

public class NetManager {
    private final String TAG = "NetManager";

    public void test() {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("http://172.20.10.2:8080/things/login.do").build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
                Log.d(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse");
                Log.d(TAG, response.body().string());
            }
        });

    }
}
