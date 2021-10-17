package com.example.myapplication.fragments.interfaceFragments;

import recyclerView.Exhibits;

//utilizo esta interfaz para pasar un objeto exhibicion de un fragments a otro
public interface IComunicationsFragment {
    public void sentExhibits(Exhibits exhibits);
}
