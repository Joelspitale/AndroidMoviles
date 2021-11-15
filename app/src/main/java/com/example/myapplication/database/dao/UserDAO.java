package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.modelo.User;
import java.util.List;
import java.util.Optional;

@Dao
public interface UserDAO {
    @Query("select * from User")
    List<User> getAllUser();

    @Query("select * from User where id=:id")
    Optional<User> findUserById(long id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
