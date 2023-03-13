package android.bignerdranch.drifting.User;

import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Mine.Mine_BianJiActivity;
import android.bignerdranch.drifting.Mine.Mine_Fragment;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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

    /**
     * 通过id获得改用户的信息
     */
    @POST("api/v1/user/id_detail")
    Call<android.bignerdranch.drifting.User.User_name_getFormID_return> getHostName(@Body android.bignerdranch.drifting.User.GetNameFormIDRequest getNameFormIDRequest,
                                                                                    @Header("Authorization") String token);
    /**
     * 获得用户创建啊的漂流本
     */
    @GET("/api/v1/drifting_note/create")
    Call<inviting_messageReturn>  getMyBook( @Header("Authorization") String token);

    /**
     * 获得创建的漂流小说
     */
    @GET("/api/v1/drifting_novel/create")
    Call<inviting_messageReturn>  getMyNovel( @Header("Authorization") String token);

    /**
     * 漂流画创建的
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_drawing/create")
    Call<inviting_messageReturn>  getMyDraw( @Header("Authorization") String token);

    /**
     * 创建的漂流相机
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_picture/create")
    Call<inviting_messageReturn>  getMyPicture( @Header("Authorization") String token);
}
