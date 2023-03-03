package android.bignerdranch.drifting.Login;

import android.bignerdranch.drifting.Mine.Friends.Friends_;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Login {

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Login_Message> login(@Field("passWord")String passWord,@Field("studentID")Integer ID);

    @GET()
    Call<Friends_> getImg(@Url String url);
}
