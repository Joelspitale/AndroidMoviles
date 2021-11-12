package com.example.myapplication.database.business;

import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.modelo.Exhibits;

import java.util.List;

public class ExhibitsBusiness implements ExhibitsRepository {

    private ExhibitsDAO exhibitsDAO;


    public ExhibitsBusiness(ExhibitsDAO exhibitsDAO) {
        this.exhibitsDAO = exhibitsDAO;
    }

    @Override
    public List<Exhibits> getAllFavorites() {
        return exhibitsDAO.getAllFavorites();
    }

    @Override
    public Exhibits deleteById(long id) {
        return exhibitsDAO.deleteById(id);
    }

    @Override
    public void insert(Exhibits exhibits) {
        exhibitsDAO.insert(exhibits);
    }

    @Override
    public void update(Exhibits exhibits) {
        exhibitsDAO.update(exhibits);
    }

    @Override
    public void delete(Exhibits exhibits) {
        exhibitsDAO.delete(exhibits);
    }
}
