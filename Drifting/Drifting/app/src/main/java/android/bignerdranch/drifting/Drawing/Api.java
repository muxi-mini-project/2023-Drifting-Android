package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.Inviting.Loading.user_drawing_request_return;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @POST("api/v1/drifting_drawing/create")
    Call<upload_return> create(@Header("Authorization") String token, @Body Image_create_message message);

    @POST("/api/v1/drifting_picture/draw")
    Call<ResponseBody> upload(@Part() RequestBody file);

    @GET("api/v1/drifting_drawing/invite")
    Call<user_drawing_request_return> request(@Header ("Authorization") String token);
}
