package android.bignerdranch.drifting.Mine.Friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FriendGetInterface {
    @GET("/api/v1/friend/get")
    Call<List<FriendFromNet>> getFriendList(@Header("Authorization")String token);
}
