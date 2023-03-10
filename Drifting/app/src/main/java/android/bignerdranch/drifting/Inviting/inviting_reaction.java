package android.bignerdranch.drifting.Inviting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface inviting_reaction {
    @POST("api/v1/drifting_drawing/accept")
    Call<inviting_reactionReturn> accept(@Header("Authorization") String token, @Body inviting_reactionBody body);

    @POST("api/v1/drifting_drawing/refuse")
    Call<inviting_reactionReturn> refuse(@Header("Authorization") String token, @Body inviting_reactionBody body);
}
