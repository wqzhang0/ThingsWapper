package com.wqzhang.thingswapper.util.net.retrofit;

import android.util.Log;

import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.net.model.ResultFormat;
import com.wqzhang.thingswapper.util.net.model.UserDTO;
import com.wqzhang.thingswapper.util.net.okhttp.OkHttpClientManager;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wqzhang on 17-3-9.
 */

public class RetrofitUtil {
    private static final String TAG = "RetrofitUtil";

    public static void test() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Common.SERVICE_HOST)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Retrofit retrofit = createRetrofit();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);

        Call<String> call = userBiz.login(new UserDTO());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });

    }

    public static void registerUser() {
        Retrofit retrofit = createRetrofit();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);

        Call<ResultFormat<UserDTO>> call = userBiz.register(new UserDTO("wqzhang", "wqzhang", "123", "wqzhang0@163.com"));
        call.enqueue(new Callback<ResultFormat<UserDTO>>() {
            @Override
            public void onResponse(Call<ResultFormat<UserDTO>> call, Response<ResultFormat<UserDTO>> response) {


            }

            @Override
            public void onFailure(Call<ResultFormat<UserDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    public static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Common.SERVICE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(OkHttpClientManager.getInstance().getCookieClient())
                .build();
        return retrofit;
    }
}
