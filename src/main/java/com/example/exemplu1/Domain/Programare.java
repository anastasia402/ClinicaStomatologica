package com.example.exemplu1.Domain;

import java.io.Serializable;

public class Programare extends Entitate implements Serializable {
    private int Idpacient;
    private String data;
    private String ora;
    private String scop;
    private static final long serialVersionUID = 8002544893149750654L;


    //public Programare(int id){
       // super(id);
    //}

    public Programare(int id, int pacient, String data, String ora, String scop) {
        super(id);
        this.Idpacient = pacient;
        this.data = data;
        this.ora = ora;
        this.scop = scop;
    }


    @Override
    public String toString() {
        return "Programare{" +
                "pacient=" + Idpacient +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                ", scop='" + scop + '\'' +
                ", ID=" + ID +
                '}';
    }

    public int getPacient() {
        return Idpacient;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getScop() {
        return scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
    }
}
