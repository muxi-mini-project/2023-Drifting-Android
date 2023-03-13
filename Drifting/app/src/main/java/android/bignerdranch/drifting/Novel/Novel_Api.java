package android.bignerdranch.drifting.Novel;

import android.bignerdranch.drifting.Inviting.inviting_reactionBody;
import android.bignerdranch.drifting.Inviting.inviting_reactionReturn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Novel_Api {

    @POST("api/v1/drifting_novel/create")
    Call<create_return> create(@Header("Authorization") String token, @Body Object message);

    @POST("api/v1/drifting_novel/write")
    Call<create_return> upload(@Header("Authorization") String token, @Body Novel_writingBody writingBody);

    @POST("api/v1/drifting_novel/invite")
    Call<inviting_reactionReturn> invite(@Header ("Authorization") String token,@Body inviting_reactionBody inviting_reactionBody);
}
