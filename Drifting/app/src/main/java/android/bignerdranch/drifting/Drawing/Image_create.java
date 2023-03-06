package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.Drawing.Image_create_message;
import android.bignerdranch.drifting.Drawing.upload_return;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Image_create {

    @POST("api/v1/drifting_drawing/create")
    Call<upload_return> create(@Header ("Authorization") String token, @Body Image_create_message message);
}
