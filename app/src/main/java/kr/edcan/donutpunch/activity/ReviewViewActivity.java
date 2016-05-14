package kr.edcan.donutpunch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.data.Game;
import kr.edcan.donutpunch.data.Review;
import kr.edcan.donutpunch.utils.DonutUtils;
import kr.edcan.donutpunch.utils.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewViewActivity extends AppCompatActivity {

    String gameId, gamename;
    TextView title, rating;
    LinearLayout reviewCardViewParent;
    NetworkService service;
    Call<Game> gameInfo;
    Call<List<Review>> reviewlists;
    ArrayList<Review> reviewArray;
    Game game;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view);
        service = DonutUtils.getInstance();
        intent = getIntent();
        gameId = intent.getStringExtra("GameId");
        gamename = intent.getStringExtra("GameName");
        setData();
    }

    private void setData() {
        gameInfo = service.getGameInfo(gameId);
        gameInfo.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if(response.code() == 200){
                    game = response.body();
                    Log.e("asdf", gamename+"asdf");
                    reviewlists = service.listReviews(gamename);
                    reviewlists.enqueue(new Callback<List<Review>>() {
                        @Override
                        public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                            if(response.code() == 200) {
                                reviewArray = new ArrayList<>(response.body());
                                setDefault();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Review>> call, Throwable t) {
                            Log.e("asdf", t.getMessage());
                        }
                    });
                } else Log.e("asdf", response.code()+"");


            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                    Log.e("asdf", t.getMessage());
            }
        });
    }


    private void setDefault() {
        title = (TextView) findViewById(R.id.review_view_title);
        rating = (TextView) findViewById(R.id.review_view_rating);
        reviewCardViewParent = (LinearLayout) findViewById(R.id.game_review_cardview_parent);
        title.setText(game.getGameName());
        rating.setText(game.getAverage_score());
        for(Review r : reviewArray){
            View view = View.inflate(getApplicationContext(), R.layout.review_cardview_layout, null);
            TextView title = (TextView) view.findViewById(R.id.review_card_title);
            TextView rating = (TextView)view.findViewById(R.id.review_card_rating);
            TextView content = (TextView) view.findViewById(R.id.review_card_content);
            title.setText(r.getWriter());
            rating.setText(r.getReview_score());
            content.setText(r.getContent());
            reviewCardViewParent.addView(view);
        }
    }
}
