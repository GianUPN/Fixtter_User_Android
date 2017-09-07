package com.fixtter.sgtel.fixtter_user_android.Servicios;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Giancarlo on 07/09/2017.
 */

public class AppPreferences {
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private Context mContext;

    private final String PREFERENCE_USER_ID = "PREFERENCE_USER_ID";
    private final String PREFERENCE_USER_EMAIL = "PREFERENCE_USER_EMAIL";
    private final String PREFERENCE_USER_NAME = "PREFERENCE_USER_NAME";
    private final String PREFERENCE_USER_LASTNAME = "PREFERENCE_USER_LASTNAME";

    public AppPreferences(Context context){
        mContext = context;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        mPrefsEditor = mSharedPrefs.edit();
    }

    public void saveUserId(int value){
        mPrefsEditor.putInt(PREFERENCE_USER_ID, value);
        mPrefsEditor.commit();
    }

    public int getUserId(){
        return mSharedPrefs.getInt(PREFERENCE_USER_ID, 0);
    }

    public void saveUserLastName(String value){
        mPrefsEditor.putString(PREFERENCE_USER_LASTNAME, value);
        mPrefsEditor.commit();
    }

    public String getUserLastName(){
        return mSharedPrefs.getString(PREFERENCE_USER_LASTNAME, null);
    }

    public void saveUserEmail(String value){
        mPrefsEditor.putString(PREFERENCE_USER_EMAIL, value);
        mPrefsEditor.commit();
    }

    public String getUserEmail(){
        return mSharedPrefs.getString(PREFERENCE_USER_EMAIL, null);
    }

    public void saveUserName(String value){
        mPrefsEditor.putString(PREFERENCE_USER_NAME, value);
        mPrefsEditor.commit();
    }

    public String getUserName(){
        return mSharedPrefs.getString(PREFERENCE_USER_NAME, null);
    }


}
