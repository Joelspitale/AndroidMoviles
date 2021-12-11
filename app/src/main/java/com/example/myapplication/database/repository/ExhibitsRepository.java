package com.example.myapplication.database.repository;

import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemMuseo;

import java.util.List;

public interface ExhibitsRepository {

    List<ItemMuseo> getAllFavorites() throws NegocioException;

    void insert(ItemMuseo itemMuseo) throws NegocioException, EncontradoException;

    public ItemMuseo load(long id) throws NegocioException, NoEncontradoException;

    void update(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException;

    void delete(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException;
}
