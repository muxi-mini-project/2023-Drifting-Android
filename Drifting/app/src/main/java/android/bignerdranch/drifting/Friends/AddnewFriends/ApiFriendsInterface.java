package android.bignerdranch.drifting.Friends.AddnewFriends;

import android.bignerdranch.drifting.Friends.DeleteFriend_call;
import android.bignerdranch.drifting.Friends.FriendsList_return;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiFriendsInterface {

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
    @POST("/api/v1/friend/pass")
    Call<AddFriends_return> agreeTobeFriend(@Body AddnewFriendSend send, @Header("Authorization")String token);

    /**
     * 删除好友
     */
    @HTTP(method = "DELETE",path = "/api/v1/friend/delete",hasBody = true)
    Call<AddFriends_return>  deleteFriends(@Body DeleteFriend_call deleteFriend_call, @Header("Authorization")String token);


}
