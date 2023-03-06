package android.bignerdranch.drifting.detail_request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface request {
    @POST("api/v1/drifting_drawing/detail")
    Call<messageReturn> drawingRequest(@Header("Authorization") String token, @Body request_body request_body);

    @POST("api/v1/drifting_note/detail")
    Call<messageReturn> bookRequest(@Header("Authorization") String token, @Body request_body request_body);

    @POST("api/v1/drifting_novel/detail")
    Call<messageReturn> novelRequest(@Header("Authorization") String token, @Body request_body request_body);

    @POST("api/v1/drifting_picture/detail")
    Call<messageReturn> cameraRequest(@Header("Authorization") String token, @Body request_body request_body);
}
