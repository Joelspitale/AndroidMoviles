package com.example.myapplication.database.repository;

import com.example.myapplication.modelo.Exhibits;
import java.util.List;

public interface ExhibitsRepository {

    List<Exhibits> getAllFavorites();

    Exhibits deleteById(long id);

    void insert(Exhibits exhibits);

    void update(Exhibits exhibits);

    void delete(Exhibits exhibits);
}
