package kr.edcan.donutpunch.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.data.User;
import kr.edcan.donutpunch.utils.DonutUtils;
import kr.edcan.donutpunch.utils.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    NetworkService service;
    static Activity activity;
    DonutUtils utils;
    Call<User> userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        activity = this;
        service = DonutUtils.getInstance();
        utils = new DonutUtils(getApplicationContext());
        String APIKEY = utils.getString(utils.API_KEY);
        if(!APIKEY.trim().equals("")) {
            userAuth = service.userAuthenticate(utils.getString(utils.API_KEY));
            userAuth.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        User user = response.body();
                        utils.setPref(utils.USER_ID, user.getId());
                        utils.setPref(utils.USER_PASSWORD, user.getPassword());
                        utils.setPref(utils.NICKNAME, user.getNickname());
                        utils.setPref(utils.ACCOUNTTYPE, user.getAccountType());
                        utils.setPref(utils.API_KEY, user.getUser_apiKey());
                        Toast.makeText(AuthActivity.this, user.getNickname() + " 님 환영합니다!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("asdf", t.getMessage()+"");
                    return;
                }
            });
        }
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }
}
