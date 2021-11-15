package com.example.myapplication.database.business;

import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.Exhibits;

import java.security.spec.ECField;
import java.util.List;
import java.util.Optional;

public class ExhibitsBusiness implements ExhibitsRepository {

    private ExhibitsDAO exhibitsDAO;


    public ExhibitsBusiness(ExhibitsDAO exhibitsDAO) {
        this.exhibitsDAO = exhibitsDAO;
    }

    @Override
    public List<Exhibits> getAllFavorites() throws NegocioException{
        try{
            return exhibitsDAO.getAllFavorites();
        }catch (Exception e){
            throw new NegocioException(e);
        }
    }
    public Exhibits load(long id) throws NegocioException, NoEncontradoException {
        Optional<Exhibits> exhibits;
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
    public void insert(Exhibits exhibits) throws NegocioException, EncontradoException {
        try {
            load(exhibits.getId());
            throw new EncontradoException("Ya existe una exhibicion con id ="+ exhibits.getId());
        }catch (NoEncontradoException e){
        }
        try {
            exhibitsDAO.insert(exhibits);
        } catch (Exception e) {
            throw new NegocioException(e);
        }

    }

    @Override
    public void update(Exhibits exhibits) throws NegocioException, NoEncontradoException{
        exhibitsDAO.update(exhibits);
    }

    @Override
    public void delete(Exhibits exhibits) throws NegocioException, NoEncontradoException {
        load(exhibits.getId());
        try{
            exhibitsDAO.delete(exhibits);
        }catch (Exception e) {
            throw new NegocioException(e);
        }

    }
}
