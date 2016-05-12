package kr.edcan.donutpunch.utils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by KOHA_CLOUD on 16. 5. 12..
 */
public interface NetworkService {
    @FormUrlEncoded
    @POST("/auth/authorize")
    Call<String> authorize (@Field("userid") String id, @Field("user_password") String password);

    @FormUrlEncoded
    @POST("/auth/unauthorize")
    Call<String> unauthorize(@Field("userid") String id);

}
