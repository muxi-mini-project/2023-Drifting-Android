package android.bignerdranch.drifting.Main.checkingNew;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface checking_new {
    @GET("api/v1/apk/get_version")
    Call<info_return> checking_new(@Header("Authorization") String token);
}
