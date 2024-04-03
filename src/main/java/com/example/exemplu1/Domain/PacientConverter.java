package com.example.exemplu1.Domain;

public class PacientConverter implements Converter<Pacient>{
    @Override
    public Pacient fromStringConverter(String line) {
        String[] tokens = line.split(",");
        return new Pacient(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]));
    }

    @Override
    public String toStringConverter(Pacient object) {
        return object.getID()+","+object.getFirst_name()+","+object.getSecond_name()+","+ object.getAge();
    }
}

