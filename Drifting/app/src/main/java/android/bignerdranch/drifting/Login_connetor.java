package android.bignerdranch.drifting;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 登录所用接口
 */
public interface Login_connetor {

    @POST("api/v1/login")
    Call<Login_return> login(@Body Login_student student);
}
