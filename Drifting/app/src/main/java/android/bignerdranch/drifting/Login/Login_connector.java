package android.bignerdranch.drifting.Login;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 登录所用接口，含:
 * 登录
 */
public interface Login_connector {

    @POST("api/v1/login")
    Call<Login_return> login(@Body Login_student student);


}
