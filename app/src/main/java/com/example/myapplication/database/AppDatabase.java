package com.example.myapplication.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.modelo.Exhibits;
import com.example.myapplication.modelo.User;

//luego tengo que agregar el del user
@Database(entities = {Exhibits.class,User.class},version = 1)
public abstract class  AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;
    public abstract ExhibitsDAO exhibitsDAO();
    public abstract UserDAO userDAO();

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE= Room.databaseBuilder(context,AppDatabase.class,"museo")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
