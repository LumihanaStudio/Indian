package kr.edcan.donutpunch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.utils.AutoLineLayout;
import kr.edcan.donutpunch.utils.ImageSingleTon;

public class GameInfoViewActivity extends AppCompatActivity {

    TextView title, releaseData, aboutGame, gamePlay;
    Toolbar toolbar;
    NetworkImageView imageview;
    Intent intent;
    String GameName, GameContent, GameReleaseDate, GameId, GameScore, GameImageURl;
    String[] GameTag;
    ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info_view);
        setDefault();
    }

    private void setDefault() {
        toolbar = (Toolbar) findViewById(R.id.game_view_toolbar);
        intent = getIntent();
        setData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">게임 세부 정보</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AutoLineLayout layout = (AutoLineLayout) findViewById(R.id.tag_autoline);
        title = (TextView) findViewById(R.id.gameview_title);
        releaseData = (TextView) findViewById(R.id.gameview_release);
        aboutGame = (TextView) findViewById(R.id.gameview_about);
        imageview = (NetworkImageView) findViewById(R.id.game_info_image);
        loader = ImageSingleTon.getInstance(this).getImageLoader();
        imageview.setImageUrl("http://iwin247.net/view/"+GameImageURl, loader);
        title.setText(GameName);
        releaseData.setText("Released in " + GameReleaseDate);
        aboutGame.setText(GameContent);
        for(String s : GameTag) {
            View view = View.inflate(this, R.layout.tag_layout, null);
            TextView tagtitle = (TextView) view.findViewById(R.id.tag_info);
            tagtitle.setText(s);
            layout.addView(view);
        }

    }

    private void setData() {
        GameName = intent.getStringExtra("GameName");
        GameContent = intent.getStringExtra("GameContent");
        GameId = intent.getStringExtra("GameId");
        GameReleaseDate = intent.getStringExtra("GameReleaseDate");
        GameScore = intent.getStringExtra("GameScore");
        GameTag = intent.getStringArrayExtra("GameTag");
        GameImageURl = intent.getStringExtra("GameImage");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
