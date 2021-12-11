package com.example.myapplication.fragments.interfaceFragments;

import com.example.myapplication.modelo.ItemMuseo;

//utilizo esta interfaz para pasar un objeto exhibicion de un fragments a otro
public interface IComunicationsFragment {
    public void sentExhibits(ItemMuseo itemMuseo);
}
