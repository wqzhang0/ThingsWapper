package com.wqzhang.thingswapper.util.net.okhttp;

import android.util.Log;

import com.google.gson.Gson;
import com.wqzhang.thingswapper.model.User_model;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.net.model.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by wqzhang on 17-3-9.
 * <p>
 * 对OkHttpClientManager 进行了一次业务逻辑的扩展
 */

public class OkHttpNetManager {
    private final String TAG = "NetManager";

    public void test() {
        OkHttpClient okHttpClient = OkHttpClientManager.getInstance().getmOkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", "wqzhang");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FormBody.Builder formBody = new FormBody.Builder().add("value", jsonObject.toString());
        RequestBody requestBody = formBody.build();
//        Request request = new Request.Builder().url("http://172.20.10.2:8080/maven-test/login.do").post(requestBody).build();
        Request request = new Request.Builder().url(Common.SERVICE_HOST).post(requestBody).build();

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

        regiest(null);
    }

    public void sendJSONMsg() {
//        OkHttpClientManager.getInstance().
    }

    /**
     * 登录
     */
    public void login() {
        OkHttpClientManager.getInstance().getAsyn("", new OkHttpClientManager.ResultCallback<UserDTO>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(UserDTO response) {

            }

        });
    }

    /**
     * 获得云端用户数据版本号
     */
    public void getCloudDataVersion() {

    }

    /**
     * 用户注册
     *
     * @param user_model
     */
    public void regiest(User_model user_model) {
        user_model = new User_model(1, "wq", "bate", "ag", "wqzhang@163", new Date());
        Gson gson = new Gson();
        String jsonObject = gson.toJson(user_model);
        Log.d(TAG, jsonObject);
    }
}
