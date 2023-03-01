package android.bignerdranch.drifting.User;

import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Mine.Mine_Fragment;

import java.io.File;
import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * 用户信息所用接口，含：
 * 获取用户信息
 * 更新用户信息
 * 上传头像
 * 获取头像
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
    @POST("api/v1/user/avatar")
    Call<Mine_Fragment.User_returnformAvatar> putUseravatar(@Part MultipartBody.Part file,
                                                            @Header("Authorization") String token);
}
