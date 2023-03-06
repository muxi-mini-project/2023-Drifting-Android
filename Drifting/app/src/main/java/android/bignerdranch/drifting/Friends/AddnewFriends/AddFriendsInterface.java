package android.bignerdranch.drifting.Friends.AddnewFriends;

import android.bignerdranch.drifting.Friends.FriendsList_return;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddFriendsInterface {

    /**
     * 添加好友的接口
     */
    @POST("/api/v1/friend/add")
    Call<AddFriends_return> getNewFriendsID(@Body AddnewFriendSend send, @Header("Authorization")String token);

    /**
     * 获得申请添加好友的列表
     */

    @GET("/api/v1/friend/request")
    Call<FriendsList_return> getWhoWbeMyFriends(@Header("Authorization")String token);//谁将会成为我的朋友

    /**
     * 是否同意添加好友
     */



}
