package kr.edcan.donutpunch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KOHA_CLOUD on 16. 5. 12..
 */
public class DonutUtils {
    public static String API_KEY = "apikey", USER_ID = "userid", USER_PASSWORD = "userpassword", ACCOUNTTYPE = "accounttype", NICKNAME = "nickname";
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public DonutUtils(Context c) {
        this.context = c;
        sharedPreferences = c.getSharedPreferences("DonutPunch", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setPref(String type, String value) {
        editor.putString(type, value);
        editor.commit();
    }

    public void setPref(String type, boolean value) {
        editor.putBoolean(type, value);
        editor.commit();

    }


    public void setPref(String type, int value) {
        editor.putInt(type, value);
        editor.commit();

    }

    public void setPref(String type, float value) {
        editor.putFloat(type, value);
        editor.commit();

    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0.0f);
    }


    public static NetworkService getInstance() {
        NetworkService networkService;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://iwin247.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);
        return networkService;
    }

    // Parse, Update
    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
