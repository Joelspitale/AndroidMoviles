package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

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

}
