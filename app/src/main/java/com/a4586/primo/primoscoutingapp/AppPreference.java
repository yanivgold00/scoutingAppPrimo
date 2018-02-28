package com.a4586.primo.primoscoutingapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 28/02/2018.
 */

public class AppPreference {
    public static final String NAME ="name";
    public static final String PASS ="password";


    private static final String APP_SHARED_PREFS = AppPreference.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    /**
     * the constructor of the class
     * @param context - the app content
     */
    public AppPreference(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    /**
     * returns the whole String of the words
     * @return the whole String of the words
     */
    public String getName() {return _sharedPrefs.getString(NAME, "");}
    public String getPass() {return _sharedPrefs.getString(PASS, "");}


    /**
     * returns an array of all the WORDS
     * @return an array of all the WORDS
     */


    /**
     * Adds the new WORDS to the list
     * @param s - the new word
     */
    public void setPass(String s) {
        _prefsEditor.putString(PASS,s);
        _prefsEditor.commit();
    }

    /**
     * Adds the new WORDS to the list
     * @param s - the new word
     */
    public void setWord(String s) {
        _prefsEditor.putString(NAME,s);
        _prefsEditor.commit();
    }
    /**
     * cleans the lists
     */


    public void clearAll()
    {
        _prefsEditor.clear();
        _prefsEditor.commit();
    }

}
