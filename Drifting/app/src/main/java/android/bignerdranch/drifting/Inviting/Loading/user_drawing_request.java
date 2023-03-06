package android.bignerdranch.drifting.Inviting.Loading;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface user_drawing_request {
        @GET("api/v1/drifting_drawing/invite")
        Call<user_drawing_request_return> request(@Header ("Authorization") String token);
}
