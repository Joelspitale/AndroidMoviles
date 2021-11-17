package com.example.myapplication.database.repository;

import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.Exhibits;
import com.example.myapplication.modelo.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUser() throws NegocioException;

    void insert(User user) throws NegocioException, EncontradoException;

    public User load(long id) throws NegocioException, NoEncontradoException;

    public User findUserByEmail(String email) throws NegocioException, NoEncontradoException;

    void update(User user) throws NegocioException, NoEncontradoException;

    void delete(User user) throws NegocioException, NoEncontradoException;
}
