package kr.edcan.donutpunch.data;

/**
 * Created by KOHA_CLOUD on 16. 5. 14..
 */
public class User {
    private String id, password, nickname, accountType, user_apiKey;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUser_apiKey() {
        return user_apiKey;
    }
}
