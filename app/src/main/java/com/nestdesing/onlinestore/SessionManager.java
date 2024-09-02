package com.nestdesign.onlinestore;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String PREF_NAME = "SessionPref";
    private static final String TOKEN = "token";
    private static final String EMAIL = "email";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String token, String email) {
        editor.putString(TOKEN, token);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
