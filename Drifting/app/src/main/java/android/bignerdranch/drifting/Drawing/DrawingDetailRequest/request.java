package android.bignerdranch.drifting.Drawing.DrawingDetailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface request {

    /**
     *
     * @param token
     * @param request_body
     * @return
     */
    @POST("api/v1/drifting_drawing/detail")
    Call<messageReturn> Request(@Header("Authorization") String token, @Body request_body request_body);
}
