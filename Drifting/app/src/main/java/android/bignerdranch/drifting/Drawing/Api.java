package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.Inviting.inviting_reactionReturn;
import android.bignerdranch.drifting.Inviting.*;

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
    Call<create_return> create(@Header("Authorization") String token, @Body Object message);

    @POST("api/v1/drifting_drawing/draw")
    Call<ResponseBody> upload(@Part() RequestBody file);

    @POST("api/v1/drifting_drawing/invite")
    Call<inviting_reactionReturn> invite(@Header ("Authorization") String token,@Body inviting_reactionBody inviting_reactionBody);
}
