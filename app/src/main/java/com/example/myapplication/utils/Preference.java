package com.example.myapplication.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;

import com.example.myapplication.R;

public class Preference {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;


    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initPreference(Context context){
        preferences = context.getSharedPreferences("SHARED_PREFEF", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setSessionActive(boolean b){
        editor.putBoolean("sessionActive", b);
        editor.commit();
    }

    public boolean getSessionActive(){
        return preferences.getBoolean("sessionActive",false);
    }

    public void savePreference(String email){
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmailSharedPreferences(){
        return preferences.getString("email","No existe ningun email registrado");
    }

    public String getPasswordSharedPreferens(){
        return preferences.getString("password","No existe ninguna password registrado");
    }
    //android.resource://com.example.myapplication/drawable/ic_groot
    public void setUriImage(Uri uri){
        String a = uri.toString();
        editor.putString("imageURI", uri.toString());
        editor.commit();
    }

    public Uri getUriImage(Context context){
        String imageUriString = preferences.getString("imageURI", "no existe uri");
        if(imageUriString.equals("no existe uri"))
            return generateUriDefault(context);

        return Uri.parse(imageUriString);
    }

    public Uri generateUriDefault(Context context){
        Resources resources = context.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.ic_groot))
                .appendPath(resources.getResourceTypeName(R.drawable.ic_groot))
                .appendPath(resources.getResourceEntryName(R.drawable.ic_groot))
                .build();
        setUriImage(uri);
        return uri;
    }



}
