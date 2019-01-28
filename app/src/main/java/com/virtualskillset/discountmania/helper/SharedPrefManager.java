package com.virtualskillset.discountmania.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.virtualskillset.discountmania.LoginActivity;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "vskilldmaina";
    private static final String KEY_Number = "keynum";
   // private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_Role = "keyrole";
    private static final String KEY_ID = "keyid";
    private static final String KEY_Status = "keystatus";
    private static final String KEY_Name = "keyname";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
       // editor.putInt(KEY_ID, user.getId());
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_Number, user.getNumber());
       // editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_Role, user.getRole());
        editor.putString(KEY_Status, user.getStatus());
        editor.putString(KEY_Name, user.getName());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Number, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
              //  sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_Number, null),
            //    sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_Role, null),
                sharedPreferences.getString(KEY_Status, null),
                sharedPreferences.getString(KEY_Name, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void logoutt() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
