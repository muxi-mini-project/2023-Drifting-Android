package android.bignerdranch.drifting.Inviting.Loading;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface inviting_request {
        @GET("api/v1/drifting_drawing/invite")
        Call<inviting_messageReturn> drawing_request(@Header ("Authorization") String token);

        @GET("api/v1/drifting_note/invite")
        Call<inviting_messageReturn> book_request(@Header ("Authorization") String token);

        @GET("api/v1/drifting_novel/invite")
        Call<inviting_messageReturn> novel_request(@Header ("Authorization") String token);

        @GET("api/v1/drifting_picture/invite")
        Call<inviting_messageReturn> camera_request(@Header ("Authorization") String token);
}
