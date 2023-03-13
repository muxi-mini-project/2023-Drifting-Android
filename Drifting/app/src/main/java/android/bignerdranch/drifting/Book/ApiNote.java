package android.bignerdranch.drifting.Book;

import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_InvitingFriends_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_InvitingFriends_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Just_join_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Participate_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Participate_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_refuseAndAgree_request;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiNote {


    /**
     * 创建漂流本接口
     * @param book_participate_request
     * @param token
     * @return
     */

    @POST("/api/v1/drifting_note/create")
    Call<Book_create_return> bookCreate(@Body Book_create_request book_participate_request,
                                        @Header("Authorization") String token);
    /**
     * 向漂流本写内容传到服务器
     * @param token
     * @return
     */

    @POST("/api/v1/drifting_note/write")
    Call<Book_Participate_return> participateNote(@Body Book_Participate_request book_participate_request,
                                                  @Header("Authorization") String token);

    /**
     * 漂流本邀请好友的部分
     * @param book_invitingFriends_request
     * @param token
     * @return
     */
    @POST("/api/v1/drifting_note/invite")
    Call<Book_InvitingFriends_return> InvitingFriendsParticipate(@Body Book_InvitingFriends_request book_invitingFriends_request,
                                                                 @Header("Authorization") String token);

    /**
     * 拒绝参加漂流本（还有一个作用就是把它从列表里面清除）
     * @param refuse_request
     * @param token
     * @return
     */
    @POST("/api/v1/drifting_note/refuse")
    Call<Book_create_return> refuseJoinBook(@Body Book_refuseAndAgree_request refuse_request,
                                            @Header("Authorization") String token);

    /**
     * 仅仅参加漂流项目
     * @param just_join_request
     * @param token
     * @return
     */

    @POST("/api/v1/drifting_note/join")
    Call<Book_create_return> justJoin(@Body Book_Just_join_request just_join_request,
                                      @Header("Authorization") String token);

    /**
     * 获得随机漂流本
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_note/recommendation")
    Call<inviting_messageReturn.data> getRandomBook(@Header("Authorization") String token);

    /**
     * 获得随机漂流相机
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_picture/recommendation")
    Call<inviting_messageReturn.data> getRandomPicture(@Header("Authorization") String token);

    /**
     * 获得随机漂流画
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_drawing/recommendation")
    Call<inviting_messageReturn.data> getRandomDraw(@Header("Authorization") String token);

    /**
     * 获得随机漂流小说
     * @param token
     * @return
     */
    @GET("/api/v1/drifting_novel/recommendation")
    Call<inviting_messageReturn.data> getRandomNovel(@Header("Authorization") String token);




}
