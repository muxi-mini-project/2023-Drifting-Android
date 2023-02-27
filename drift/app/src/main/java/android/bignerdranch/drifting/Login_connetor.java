package android.bignerdranch.drifting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 登录所用接口
 */
public interface Login_connetor {

    @FormUrlEncoded
    @POST("api/v1/login")
    Call<Login_return> login(@Field("studentID") Integer id,
                              @Field("passWord") String password);
}
