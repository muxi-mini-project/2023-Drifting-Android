package android.bignerdranch.drifting.User;

import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Mine.Mine_BianJiActivity;
import android.bignerdranch.drifting.Mine.Mine_Fragment;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * 用户信息所用接口，含：
 * 获取用户信息
 * 更新用户信息
 * 上传头像
 */
public interface User_connector {
    /**
     * 登录后通过token获取用户信息
     * @param token 登录后获取到的token
     * @return
     */
    @GET("api/v1/user/detail")
    Call<Login_LoginActivity.User_returnAll> getUserMes(@Header("Authorization") String token);

    /**
     * 登录后通过token上传用户头像
     */
    @Multipart
    @PUT("api/v1/user/avatar")
    Call<Mine_Fragment.User_returnformAvatar> putUseravatar(@Part MultipartBody.Part body,
                                                            @Header("Authorization") String token);

    /**
     * 登录后通过token修改用户信息
     */
    @PUT("api/v1/user/update")
    Call<Mine_BianJiActivity.Return_fromdata> putUserdata(@Body() User_Putdata user_putdata,
                                                          @Header("Authorization") String token);
}
