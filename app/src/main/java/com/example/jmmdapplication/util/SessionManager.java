package com.example.jmmdapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String USER_SESSION_PREFS = "UserSessionPrefs";
    private static final String USER_ID_KEY = "UserIdKey";

    public static void saveUserSession(Context context, int userId) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    public static void clearUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_ID_KEY);
        editor.apply();
    }

    public static int getUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }
}
