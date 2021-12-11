package com.example.myapplication.database.business;

import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemMuseo;

import java.util.List;
import java.util.Optional;

public class ExhibitsBusiness implements ExhibitsRepository {

    private ExhibitsDAO exhibitsDAO;


    public ExhibitsBusiness(ExhibitsDAO exhibitsDAO) {
        this.exhibitsDAO = exhibitsDAO;
    }

    @Override
    public List<ItemMuseo> getAllFavorites() throws NegocioException{
        try{
            return exhibitsDAO.getAllFavorites();
        }catch (Exception e){
            throw new NegocioException(e);
        }
    }
    public ItemMuseo load(long id) throws NegocioException, NoEncontradoException {
        Optional<ItemMuseo> exhibits;
        try {
            exhibits = exhibitsDAO.findFavoritesById(id);
        }catch (Exception e){
            throw new NegocioException(e);
        }
        if (!exhibits.isPresent()) {
            throw new NoEncontradoException("No se encuentra la Exhibicion con id=" + id);
        }
        return exhibits.get();
    }

    @Override
    public void insert(ItemMuseo itemMuseo) throws NegocioException, EncontradoException {
        try {
            load(itemMuseo.getId());
            throw new EncontradoException("Ya existe una exhibicion con id ="+ itemMuseo.getId());
        }catch (NoEncontradoException e){
        }
        try {
            exhibitsDAO.insert(itemMuseo);
        } catch (Exception e) {
            throw new NegocioException(e);
        }

    }

    @Override
    public void update(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException{
        exhibitsDAO.update(itemMuseo);
    }

    @Override
    public void delete(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException {
        load(itemMuseo.getId());
        try{
            exhibitsDAO.delete(itemMuseo);
        }catch (Exception e) {
            throw new NegocioException(e);
        }

    }
}
