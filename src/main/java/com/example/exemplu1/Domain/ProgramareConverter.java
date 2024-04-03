package com.example.exemplu1.Domain;

public class ProgramareConverter implements Converter<Programare>{
    @Override
    public Programare fromStringConverter(String line) {
        String[] tokens = line.split(",");
        return new Programare(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), tokens[2], tokens[3], tokens[4]);
    }

    @Override
    public String toStringConverter(Programare object) {
        return object.getID()+","+ object.getPacient()+","+object.getData()+","+object.getOra()+","+object.getScop();
    }
}
