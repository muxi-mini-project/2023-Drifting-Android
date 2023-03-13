package android.bignerdranch.drifting.Camera;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 漂流相机所用接口，包含：
 *接受创作邀请
 * 获取用户创建的漂流相机
 * 创建漂流相机
 * 获取漂流相机内容
 * 创作相机照片
 * 获取邀请信息
 * 邀请好友进行创作
 * 获取用户参加的漂流相机信息（不含自己创建的）
 * 参加漂流相机创作（仅参加）
 * 随机推荐漂流相机
 * 拒绝邀请
 */
public interface Camera_connector {
    /**
     * 接受创作邀请(仅负责删除对应邀请记录，后续需要调用创作接口)
     */
    @POST("api/v1/drifting_picture/accept")
    Call<Camera_return_upload.Camera_accept_inviting_return> AcceptCamera_inviting(@Body Camera_return_upload.Camera_accept_inviting body,
                                                                                   @Header("Authorization") String token);

    /**
     * 获取用户创建的漂流相机
     */
    @GET("api/v1/drifting_picture/create")
    Call<Camera_return_upload.Camera_return_usermessage> GetCamera_mes_user(@Header("Authorization") String token);

    /**
     * 创建漂流相机
     */
    @POST("api/v1/drifting_picture/create")
    Call<Camera_return_upload.Camera_return_create> CreateCamera_new(@Body Camera_return_upload.Camera_upload_create body,
                                                                     @Header("Authorization") String token);

    /**
     *获取漂流相机内容
     */
    @HTTP(method = "POST",path = "/api/v1/drifting_picture/detail",hasBody = true)
    Call<Camera_return_upload.Camera_return_mes_body> GetCamera_mes_body(@Body Camera_return_upload.Camera_upload_mes_body body,
                                                                         @Header("Authorization") String token);
    /**
     * 创作漂流相机
     */
    @Multipart
    @POST("api/v1/drifting_picture/draw")
    Call<Camera_return_upload.Camera_return_make> MakeCamera(@Part MultipartBody.Part body,
                                                             @Part("file_id") Long id,
                                                             @Header("Authorization") String token);

    /**
     * 获取邀请信息
     */
    @GET("api/v1/drifting_picture/invite")
    Call<Camera_return_upload.Camera_return_inviting> GetCamera_inviting(@Header("Authorization") String token);

    /**
     * 邀请好友进行创作
     */
    @POST("api/v1/drifting_picture/invite")
    Call<Camera_return_upload.Camera_return_inviting_friend> Camera_inviting_friend(@Body Camera_return_upload.Camera_upload_inviting_friend body,
                                                                                    @Header("Authorization") String token);

    /**
     * 获取用户参加的漂流照片信息(不含自己创建的)
     */
    @GET("api/v1/drifting_picture/join")
    Call<Camera_return_upload.Camera_return_join_mes> Camera_return_join_mes(@Header("Authorization") String token);

    /**
     * 参加漂流相机创作（仅参加）
     */
    @POST("api/v1/drifting_picture/join")
    Call<Camera_return_upload.Camera_return_accept_random> Camera_accept_random(@Body Camera_return_upload.Camera_upload_accept_random body,
                                                                                @Header("Authorization") String token);

    /**
     * 随机推荐漂流相机
     */
    @GET("api/v1/drifting_picture/recommendation")
    Call<Camera_return_upload.Camera_return_recommand_random> Camera_recommand_random(@Header("Authorization") String token);

    /**
     * 拒绝邀请
     */
    @POST("api/v1/drifting_picture/refuse")
    Call<Camera_return_upload.Camera_return_refuse_inviting> Camera_refuse_inviting(@Body Camera_return_upload.Camera_upload_refuse_inviting body,
                                                                                    @Header("Authorization") String token);
}
