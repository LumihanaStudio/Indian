package kr.edcan.donutpunch.utils;

import java.util.List;

import kr.edcan.donutpunch.data.Game;
import kr.edcan.donutpunch.data.Review;
import kr.edcan.donutpunch.data.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by KOHA_CLOUD on 16. 5. 12..
 */
public interface NetworkService {
    @FormUrlEncoded
    @POST("/auth/login")
    Call<User> userLogin(@Field("id") String id, @Field("password") String password);

    // AccountType = developer / user
    @FormUrlEncoded
    @POST("/auth/register")
    Call<User> userSignin(@Field("id") String id, @Field("password") String password, @Field("nickname") String nickname,
                          @Field("account") String accountType);

    @FormUrlEncoded
    @POST("/auth/authenticate")
    Call<User> userAuthenticate(@Field("key") String apikey);

    @FormUrlEncoded
    @POST("/gameregister")
    Call<Game> registerGame(@Field("writer") String username, @Field("gamename") String gameName,
                            @Field("content") String content, @Field("tag") String tag, @Field("date") String launch_date);

    @FormUrlEncoded
    @POST("/reviewregister")
    Call<Game> registerReview(@Field("writer") String username, @Field("gamename") String gameName,
                              @Field("score") int score, @Field("content") String content);

    @FormUrlEncoded
    @POST("/reviewlist")
    Call<List<Review>> listReviews(@Field("gamename") String gameName);

    @POST("/gamelist")
    Call<List<Game>> listGames();

    @POST("/gamelist/{search}")
    Call<Game> getGameInfo(@Path("search") String gameId);

    @FormUrlEncoded
    @POST("/search")
    Call<List<Game>> searchGame(@Field("query") String query);

    @FormUrlEncoded
    @POST("/tagsearch")
    Call<List<Game>> tagSearch(@Field("tag") String query);


    @POST("/currentreview")
    Call<List<Review>> getRecentReviews();

}
