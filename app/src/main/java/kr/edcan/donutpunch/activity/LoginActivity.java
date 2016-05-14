package kr.edcan.donutpunch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.data.User;
import kr.edcan.donutpunch.utils.DonutUtils;
import kr.edcan.donutpunch.utils.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText id, pw;
    TextView confirm;
    NetworkService service;
    DonutUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        service = DonutUtils.getInstance();
        utils = new DonutUtils(getApplicationContext());
        setDefault();
    }

    private void setDefault() {
        id = (EditText) findViewById(R.id.login_idinput);
        pw = (EditText) findViewById(R.id.login_pwinput);
        confirm = (TextView) findViewById(R.id.login_confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_confirm:
                startLogin();
                break;
        }
    }

    private void startLogin() {
        String idString = id.getText().toString().trim();
        String pwString = pw.getText().toString().trim();
        if(!idString.equals("") && pwString.equals("")){
            Toast.makeText(LoginActivity.this, "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            Call<User> login = service.userLogin(idString, pwString);
            login.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code() == 200) {
                        User user = response.body();
                        utils.setPref(utils.USER_ID, user.getId());
                        utils.setPref(utils.USER_PASSWORD, user.getPassword());
                        utils.setPref(utils.NICKNAME, user.getNickname());
                        utils.setPref(utils.ACCOUNTTYPE, user.getAccountType());
                        utils.setPref(utils.API_KEY, user.getUser_apiKey());

                        Toast.makeText(LoginActivity.this, user.getNickname() + " 님 환영합니다!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        AuthActivity.activity.finish();
                    } else Toast.makeText(LoginActivity.this, "로그인 정보를 확인해주세요", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
