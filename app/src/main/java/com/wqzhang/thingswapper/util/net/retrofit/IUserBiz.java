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
    String prefix = "User/";

    @POST(prefix + "JsonLogin.do")
    Call<ResultFormat<UserDTO>> login(@Body UserDTO userDTO);


    @POST(prefix + "JsonRegister.do")
    Call<ResultFormat<UserDTO>> register(@Body UserDTO userDTO);
}
