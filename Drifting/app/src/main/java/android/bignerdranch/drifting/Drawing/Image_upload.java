package android.bignerdranch.drifting.Drawing;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Image_upload {

    @POST("/api/v1/drifting_picture/draw")
    Call<ResponseBody> upload(@Part() RequestBody file);
}
