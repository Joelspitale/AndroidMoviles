package com.example.myapplication.utils;

import static com.example.myapplication.utils.Constants.MAX_SIZE_PASSWORD;
import static com.example.myapplication.utils.Constants.MIN_SIZE_PASSWORD;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Patterns;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.database.repository.UserRepository;

public class Tools {
    private Preference preferences = new Preference();

    public boolean verifyConnection(Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNameOrLastNameCorrect(String nameOrLastName){
        if(nameOrLastName.trim().length()==0)
            return false;
        return true;
    }

    public boolean isPasswordValidate(String password) {
        if (password.length() >= MIN_SIZE_PASSWORD && password.length() <= MAX_SIZE_PASSWORD)
            return true;
        return false;
    }
    public UserRepository getRepositoryUser(Activity activity){
        AppDatabase db = AppDatabase.getInstance(activity.getBaseContext());
        UserDAO userDAO = db.userDAO();
        return new UserBusinnes(userDAO);
    }

    public ExhibitsRepository getRepositoryItemUseo(Activity activity) {
        AppDatabase db = AppDatabase.getInstance(activity.getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        return new ExhibitsBusiness(exhibitsDAO);
    }

    public boolean isEmailValidate(String correo) {
        if(Patterns.EMAIL_ADDRESS.matcher(correo).matches())
            return true;
        return false;
    }
}
