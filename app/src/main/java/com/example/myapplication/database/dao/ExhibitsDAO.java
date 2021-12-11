package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.modelo.ItemMuseo;
import java.util.List;
import java.util.Optional;

@Dao
public interface ExhibitsDAO {
    @Query("select * from ItemMuseo")
    List<ItemMuseo> getAllFavorites();

    @Query("select * from ItemMuseo where id=:id")
    Optional<ItemMuseo> findFavoritesById(long id);

    @Insert
    void insert(ItemMuseo itemMuseo);

    @Update
    void update(ItemMuseo itemMuseo);

    @Delete
    void delete(ItemMuseo itemMuseo);
}
