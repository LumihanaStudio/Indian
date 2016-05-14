package kr.edcan.donutpunch.activity;

import android.app.WallpaperInfo;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.LoginException;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.adapter.PagerAdapterClass;
import kr.edcan.donutpunch.data.Game;
import kr.edcan.donutpunch.data.Review;
import kr.edcan.donutpunch.utils.DonutUtils;
import kr.edcan.donutpunch.utils.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView mainClick, suggestClick;
    ViewPager newPager, suggestPager;
    ArrayList<String> new_title, new_content, new_image;
    ArrayList<String> sug_title, sug_content, sug_image;
    int[] firstCircle = {R.id.first_pager_1, R.id.first_pager_2, R.id.first_pager_3, R.id.first_pager_4, R.id.first_pager_5};
    int[] secondCircle = {R.id.second_pager_1, R.id.second_pager_2, R.id.second_pager_3, R.id.second_pager_4, R.id.second_pager_5};
    Toolbar toolbar;
    Call<String> login;
    Call<List<Review>> reviewList;
    MaterialSearchView searchView;
    NetworkService service;
    ArrayList<Game> gamelists;
    ArrayList<Review> reviewlists;
    LinearLayout recentParentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }

    private void loadData() {
        service = DonutUtils.getInstance();
        Call<List<Game>> getGameList = service.listGames();
        getGameList.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.code() == 200) {
                    gamelists = new ArrayList<Game>(response.body());
                    setDefault();
                } else
                    Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
            }
        });
        reviewList = service.getRecentReviews();
        reviewList.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.code() == 200) {
                    reviewlists = new ArrayList<Review>(response.body());
                    setRecentReviews();
                } else Log.e("asdf", response.code()+"");
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    private void setRecentReviews() {
        recentParentLayout = (LinearLayout) findViewById(R.id.recent_parentlayout);
        for(Review r : reviewlists){
            View view = View.inflate(getApplicationContext(), R.layout.main_listview_content, null);
            NetworkImageView imageview = (NetworkImageView) view.findViewById(R.id.main_listview_imageview);
            TextView title = (TextView) view.findViewById(R.id.main_listview_title);
            TextView content=  (TextView) view.findViewById(R.id.main_listview_tags);
            TextView rating = (TextView) view.findViewById(R.id.main_listview_rating);
            TextView asdf = (TextView) view.findViewById(R.id.main_listview_id);
            title.setText(r.getReview_game());
            content.setText(r.getContent());
            rating.setText(r.getReview_score());
            asdf.setText(r.get_id());
            view.setOnClickListener(reviewClick);
            recentParentLayout.addView(view);
        }
    }

    View.OnClickListener reviewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView asdf = (TextView) v.findViewById(R.id.main_listview_id);
            TextView title= (TextView) v.findViewById(R.id.main_listview_title);

            Log.e("asdf", asdf.getText().toString().trim());
            startActivity(new Intent(getApplicationContext(), ReviewViewActivity.class).putExtra("GameId", asdf.getText().toString().trim())
            .putExtra("GameName", title.getText().toString().trim()));

        }
    };
    private void setDefault() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.searchView);
        newPager = (ViewPager) findViewById(R.id.new_indiegame_viewpager);
        suggestPager = (ViewPager) findViewById(R.id.suggest_indiegame_viewpager);
        mainClick = (TextView) findViewById(R.id.main_banner_click);
        suggestClick = (TextView) findViewById(R.id.main_banner_under_click);
        mainClick.setOnClickListener(newBannerClick);
        suggestClick.setOnClickListener(suggestBannerClick);
        new_title = new ArrayList<>();
        new_content = new ArrayList<>();
        new_image = new ArrayList<>();
        sug_title = new ArrayList<>();
        sug_content = new ArrayList<>();
        sug_image = new ArrayList<>();
        for (Game a : gamelists) {
            new_title.add(a.getGameName());
            new_content.add(a.getContent());
            new_image.add(a.getFileList().get(0).getFilename());
        }
        sug_title = new ArrayList<>(new_title);
        sug_content = new ArrayList<>(new_content);
        sug_image = new ArrayList<>(new_image);
        Collections.reverse(sug_content);
        Collections.reverse(sug_title);
        Collections.reverse(sug_image);

        newPager.setAdapter(new PagerAdapterClass(getApplicationContext(), new_title, new_content, new_image));
        suggestPager.setAdapter(new PagerAdapterClass(getApplicationContext(), sug_title, sug_content, sug_image));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        searchView.setHint("게임 및 #태그 검색");
        newPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < 5; i++) {
                    ImageView view = (ImageView) findViewById(firstCircle[i]);
                    view.setImageDrawable(getResources().getDrawable((position == i) ? R.drawable.round_oval : R.drawable.round_oval_dark));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        suggestPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 5; i++) {
                    ImageView view = (ImageView) findViewById(secondCircle[i]);
                    view.setImageDrawable(getResources().getDrawable((position == i) ? R.drawable.round_oval : R.drawable.round_oval_dark));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    View.OnClickListener newBannerClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), GameInfoViewActivity.class)
                    .putExtra("GameName", gamelists.get(newPager.getCurrentItem()).getGameName())
                    .putExtra("GameContent", gamelists.get(newPager.getCurrentItem()).getContent())
                    .putExtra("GameReleaseDate", gamelists.get(newPager.getCurrentItem()).getLaunch_date())
                    .putExtra("GameId", gamelists.get(newPager.getCurrentItem()).get_id())
                    .putExtra("GameImage", new_image.get(newPager.getCurrentItem()))
                    .putExtra("GameTag", gamelists.get(newPager.getCurrentItem()).getTag())
                    .putExtra("GameScore", gamelists.get(newPager.getCurrentItem()).getAverage_score()));
        }
    };
    View.OnClickListener suggestBannerClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), GameInfoViewActivity.class)
                    .putExtra("GameName", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).getGameName())
                    .putExtra("GameContent", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).getContent())
                    .putExtra("GameReleaseDate", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).getLaunch_date())
                    .putExtra("GameId", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).get_id())
                    .putExtra("GameImage", new_image.get(4 - suggestPager.getCurrentItem()))
                    .putExtra("GameTag", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).getTag())
                    .putExtra("GameScore", gamelists.get(gamelists.size() - 1 - suggestPager.getCurrentItem()).getAverage_score()));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
