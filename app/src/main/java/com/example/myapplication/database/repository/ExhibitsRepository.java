package com.example.myapplication.database.repository;

import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.Exhibits;
import java.util.List;

public interface ExhibitsRepository {

    List<Exhibits> getAllFavorites() throws NegocioException;

    void insert(Exhibits exhibits) throws NegocioException, EncontradoException;

    public Exhibits load(long id) throws NegocioException, NoEncontradoException;

    void update(Exhibits exhibits) throws NegocioException, NoEncontradoException;

    void delete(Exhibits exhibits) throws NegocioException, NoEncontradoException;
}
