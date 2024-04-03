package com.example.exemplu1.Domain;

public interface Converter<T extends Entitate>{
    public T fromStringConverter(String line);
    public String toStringConverter(T object);
}
