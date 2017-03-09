package com.wqzhang.thingswapper.util.net.retrofit;

import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.util.net.model.ResultFormat;
import com.wqzhang.thingswapper.util.net.model.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wqzhang on 17-3-9.
 */

public interface IUserBiz {

    @POST("Jsonlogin.do")
    Call<String> login(@Body UserDTO userDTO);


    @POST("JsonRegister.do")
    Call<ResultFormat<UserDTO>> register(@Body UserDTO userDTO);
}
