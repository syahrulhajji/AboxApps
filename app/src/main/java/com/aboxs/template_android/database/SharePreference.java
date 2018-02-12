package com.aboxs.template_android.database;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Abox's on 11/02/2018.
 */

public class SharePreference {

    public static final String PREFERENCE_NAME = "vjhytgdfkj";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    private SharedPreferences sharedpreferences;

    public SharePreference(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public String getName() {
        String count = sharedpreferences.getString(NAME, null);
        return count;
    }

    public void saveName(String name) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getImage() {
        String count = sharedpreferences.getString(IMAGE, "image");
        return count;
    }

    public void saveImage(String id) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(IMAGE, id);
        editor.commit();
    }

    public void clearSharePreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(NAME);
        editor.remove(IMAGE);
        editor.commit();
    }
}
