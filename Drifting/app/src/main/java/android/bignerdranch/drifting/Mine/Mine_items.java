package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.Camera.Camera_return_upload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;

public interface Mine_items {
    /**
     * 获取用户创建的漂流相机
     */
    @GET("api/v1/drifting_picture/create")
    Call<Camera_return_upload.Items_return_usermessage> GetCamera_user_create(@Header("Authorization") String token);
    /**
     * 获取用户参加的漂流照片信息(不含自己创建的)
     */
    @GET("api/v1/drifting_picture/join")
    Call<Camera_return_upload.Items_return_usermessage> GetCamera_user_attend(@Header("Authorization") String token);

    /**
     * 获取用户创建的漂流本
     */
    @GET("api/v1/drifting_note/create")
    Call<Camera_return_upload.Items_return_usermessage> GetBook_user_create(@Header("Authorization") String token);

    /**
     * 获取用户参与的漂流本
     */
    @GET("api/v1/drifting_note/join")
    Call<Camera_return_upload.Items_return_usermessage> GetBook_user_attend(@Header("Authorization") String token);

    /**
     * 获取用户创建的漂流画
     */
    @GET("api/v1/drifting_drawing/create")
    Call<Camera_return_upload.Items_return_usermessage> GetDrawing_user_create(@Header("Authorization") String token);

    /**
     * 获取用户创建的漂流画
     */
    @GET("api/v1/drifting_drawing/join")
    Call<Camera_return_upload.Items_return_usermessage> GetDrawing_user_attend(@Header("Authorization") String token);

    /**
     * 获取用户创建的漂流画
     */
    @GET("api/v1/drifting_novel/create")
    Call<Camera_return_upload.Items_return_usermessage> GetNovel_user_create(@Header("Authorization") String token);

    /**
     * 获取用户创建的漂流画
     */
    @GET("api/v1/drifting_novel/join")
    Call<Camera_return_upload.Items_return_usermessage> GetNovel_user_attend(@Header("Authorization") String token);

    /**
     * 删除漂流相机
     */
    @HTTP(method = "DELETE",path ="api/v1/drifting_picture/delete",hasBody = true )
    Call<Camera_return_upload.Delete_return> Delete_Camera(@Body Camera_return_upload.Delete_upload id,
                                                           @Header("Authorization") String token);
    /**
     * 删除漂流本
     */
    @HTTP(method = "DELETE",path ="api/v1/drifting_note/delete",hasBody = true )
    Call<Camera_return_upload.Delete_return> Delete_Note(@Body Camera_return_upload.Delete_upload id,
                                                           @Header("Authorization") String token);
    /**
     * 删除漂流小说
     */
    @HTTP(method = "DELETE",path ="api/v1/drifting_novel/delete",hasBody = true )
    Call<Camera_return_upload.Delete_return> Delete_Novel(@Body Camera_return_upload.Delete_upload id,
                                                           @Header("Authorization") String token);
    /**
     * 删除漂流画
     */
    @HTTP(method = "DELETE",path ="api/v1/drifting_drawing/delete",hasBody = true )
    Call<Camera_return_upload.Delete_return> Delete_Drawing(@Body Camera_return_upload.Delete_upload id,
                                                           @Header("Authorization") String token);
}
