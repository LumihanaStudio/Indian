package kr.edcan.donutpunch.activity;

import android.graphics.Color;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final int selectedBackground = R.drawable.round_square_login_whitebg;
    boolean isDeveloper = false;
    TextView normalUser, gameDeveloper, confirmNext;
    EditText id, pw, repw, nickname;
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setDefault();
    }

    private void setDefault() {
        service = DonutUtils.getInstance();
        normalUser = (TextView) findViewById(R.id.register_normaluser);
        gameDeveloper = (TextView) findViewById(R.id.register_developer);
        confirmNext = (TextView) findViewById(R.id.register_confirm);
        id = (EditText) findViewById(R.id.register_idinput);
        pw = (EditText) findViewById(R.id.register_pwinput);
        repw = (EditText) findViewById(R.id.register_pwreinput);
        nickname = (EditText) findViewById(R.id.register_nickname);

        // OnClick Listeners
        normalUser.setOnClickListener(this);
        confirmNext.setOnClickListener(this);
        gameDeveloper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_normaluser:
                setRegisterType(false);
                break;
            case R.id.register_developer:
                setRegisterType(true);
                break;
            case R.id.register_confirm:
                setLogin();
                break;
        }
    }

    private void setLogin() {
        String idString = id.getText().toString().trim();
        String pwString = pw.getText().toString().trim();
        String pwReString = repw.getText().toString().trim();
        String nicknameString = nickname.getText().toString().trim();
        if (idString.equals("") || pwString.equals("") || pwReString.equals("") || nicknameString.equals("")) {
            Toast.makeText(RegisterActivity.this, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (!pwString.equals(pwReString)) {
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            } else {
                Call<User> userRegister = service.userSignin(idString, pwString, nicknameString, (isDeveloper) ? "developer" : "user");
                userRegister.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
                            Toast.makeText(RegisterActivity.this, "가입에 성공했습니다. 로그인해주세요", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (response.code() == 409) {
                            Toast.makeText(RegisterActivity.this, "중복된 계정입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("asdf", response.code()+"");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void setRegisterType(boolean isDeveloper) {
        this.isDeveloper = isDeveloper;
        if (isDeveloper) {
            gameDeveloper.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            gameDeveloper.setBackground(getResources().getDrawable(selectedBackground));
            normalUser.setTextColor(Color.WHITE);
            normalUser.setBackground(null);
        } else {
            normalUser.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            normalUser.setBackground(getResources().getDrawable(selectedBackground));
            gameDeveloper.setTextColor(Color.WHITE);
            gameDeveloper.setBackground(null);
        }
    }

}
