package pe.com.gmdsa.sedapal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by glarab on 01/06/16.
 * Clase para gestionar las preferencias de la app
 */

public class AppPreferences {

    //archivo de preferencias
    private static final String PREF_NAME = "gmd_app_name";
    //declarar constantes finales llaves de preferenceias
    public static final String PREF_ISUSERLOGGED = "IsUserLogged";
    public static final String PREF_USERID = "UserId";
    public static final String BACKFROMBACKGROUND = "frombackground";
    public static final String PREF_SOCKET_TIMEOUT = "SocketTimeout";
    public static final String FLAG_ONBOARDING = "flag_onboarding";

    public static AppPreferences appPreferences;
    public Context context;
    public SharedPreferences sharedPreferences;

    public static AppPreferences getInstance(final Context context) {
        if (appPreferences == null) {
            appPreferences = new AppPreferences(context);
        }
        return appPreferences;
    }

    public AppPreferences(final Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean _IsLogged() {
        boolean booIsLogged = sharedPreferences.getBoolean(PREF_ISUSERLOGGED, false);
        return booIsLogged;
    }

    public int _loadUserId() {
        int intUserId = sharedPreferences.getInt(PREF_USERID, -1);
        return intUserId;
    }

    public int _loadSocketTimeout() {
        int intUserId = sharedPreferences.getInt(PREF_SOCKET_TIMEOUT, Constants.DEFAULT_SOCKET_TIMEOUT);
        return intUserId;
    }

    public Boolean _IsBackFromBackground() {
        return sharedPreferences.getBoolean(BACKFROMBACKGROUND, false);
    }


    //Guardar preferencias
    public void savePreference(final String strPrefKey, final Object objPrefValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (objPrefValue instanceof String) {
            editor.putString(strPrefKey, (String) objPrefValue);
        } else if (objPrefValue instanceof Boolean) {
            editor.putBoolean(strPrefKey, (Boolean) objPrefValue);
        } else if (objPrefValue instanceof Integer) {
            editor.putInt(strPrefKey, (Integer) objPrefValue);
        } else if (objPrefValue instanceof Long) {
            editor.putLong(strPrefKey, (Long) objPrefValue);
        } else if (objPrefValue instanceof Float) {
            editor.putFloat(strPrefKey, (Float) objPrefValue);
        }


        editor.commit();
    }


    /**
     * Guardar y recuperar preferencaias por activity
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).apply();
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).apply();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clear(Context context, SharedPreferences p) {
        SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }


}
