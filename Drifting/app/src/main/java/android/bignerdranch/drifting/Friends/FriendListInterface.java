package android.bignerdranch.drifting.Friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FriendListInterface {
    /**
     * 获得好友列表的接口
     */
    @GET("/api/v1/friend/get")
    Call<FriendsList_return> getFriendList(@Header("Authorization") String token);
}
