package android.bignerdranch.drifting.Drawing;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Image_upload {
    @Multipart
    @POST("api/v1/drifting_drawing/draw")
    Call<create_return> upload(@Part ArrayList<MultipartBody.Part> body, @Header("Authorization") String token);
}
