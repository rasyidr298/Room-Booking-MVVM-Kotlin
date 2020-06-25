package com.rrdev.roombookingmvvm.data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefToken {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    private static SharedPrefToken mInstance;
    private static Context context;

    private SharedPrefToken(Context context) {
        SharedPrefToken.context = context;
    }

    public static synchronized SharedPrefToken getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefToken(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public void saveDeviceToken(String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, "trial Token dilihat di SharePref Token");
    }

}
