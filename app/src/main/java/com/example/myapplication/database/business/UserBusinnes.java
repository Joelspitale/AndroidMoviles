package com.example.myapplication.database.business;

import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import java.util.List;
import java.util.Optional;

public class UserBusinnes implements UserRepository {

    private UserDAO userDAO;

    public UserBusinnes(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUser() throws NegocioException {
        try{
            return userDAO.getAllUser();
        }catch (Exception e){
            throw new NegocioException(e);
        }
    }

    @Override
    public User load(long id) throws NegocioException, NoEncontradoException {
        Optional<User> user;
        try {
            user = userDAO.findUserById(id);
        }catch (Exception e){
            throw new NegocioException(e);
        }
        if (!user.isPresent()) {
            throw new NoEncontradoException("No se encuentra el user con id=" + id);
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email) throws NegocioException, NoEncontradoException {
        Optional<User> user;
        try {
            user = userDAO.findUserByEmail(email);
        }catch (Exception e){
            throw new NegocioException(e);
        }
        if (!user.isPresent()) {
            throw new NoEncontradoException("No se encuentra el user con email=" + email);
        }
        return user.get();
    }

    @Override
    public void insert(User user) throws NegocioException, EncontradoException {
        try {
            load(user.getId());
            throw new EncontradoException("Ya existe un user con id ="+ user.getId());
        }catch (NoEncontradoException e){
        }
        try {
            userDAO.insert(user);
        } catch (Exception e) {
            throw new NegocioException(e);
        }
    }

    @Override
    public void update(User user) throws NegocioException, NoEncontradoException {
        userDAO.update(user);
    }

    @Override
    public void delete(User user) throws NegocioException, NoEncontradoException {
        load(user.getId());
        try{
            userDAO.delete(user);
        }catch (Exception e) {
            throw new NegocioException(e);
        }
    }
}
