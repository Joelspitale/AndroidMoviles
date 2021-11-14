package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.modelo.Exhibits;
import java.util.List;
import java.util.Optional;

@Dao
public interface ExhibitsDAO {
    @Query("select * from Exhibits")
    List<Exhibits> getAllFavorites();

    @Query("select * from Exhibits where id=:id")
    Optional<Exhibits> findFavoritesById(long id);

    @Insert
    void insert(Exhibits exhibits);

    @Update
    void update(Exhibits exhibits);

    @Delete
    void delete(Exhibits exhibits);
}
