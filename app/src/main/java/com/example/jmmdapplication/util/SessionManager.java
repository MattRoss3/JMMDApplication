package com.example.jmmdapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing user session data using {@link SharedPreferences}.
 * <p>
 * This class provides methods for saving, clearing, and retrieving the user session ID.
 * </p>
 */
public class SessionManager {

    private static final String USER_SESSION_PREFS = "UserSessionPrefs";
    private static final String USER_ID_KEY = "UserIdKey";

    /**
     * Saves the user ID in shared preferences to manage the user session.
     *
     * @param context The context used to access shared preferences.
     * @param userId The user ID to be saved in the session.
     */

    public static void saveUserSession(Context context, int userId) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    /**
     * Clears the user ID from shared preferences, effectively ending the user session.
     *
     * @param context The context used to access shared preferences.
     */

    public static void clearUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_ID_KEY);
        editor.apply();
    }

    /**
     * Retrieves the user ID from shared preferences.
     *
     * @param context The context used to access shared preferences.
     * @return The user ID if present, otherwise returns -1 indicating no session.
     */

    public static int getUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(USER_ID_KEY, -1);
    }
}
