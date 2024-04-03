package com.example.exemplu1.Domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {
    protected int ID;
    private static final long serialVersionUID = 8002544893149750654L;
    public Entitate(){}
    public Entitate(int id){
        this.ID = id;
    }

    public int getID() {
        return this.ID;
    }
}
