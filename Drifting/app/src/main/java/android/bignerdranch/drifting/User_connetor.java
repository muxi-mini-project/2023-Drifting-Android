package android.bignerdranch.drifting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * 用户信息所用接口，含：
 * 获取用户信息
 * 更新用户信息
 * 上传头像
 * 获取头像
 */
public interface User_connetor {
    /**
     * 登录后通过token获取用户信息
     * @param token 登录后获取到的token
     * @return
     */
    @GET("api/v1/user/detail")
    Call<User_return> getUserMes(@Header("Authorization") String token);

    /**
     * 登录后
     */
}
