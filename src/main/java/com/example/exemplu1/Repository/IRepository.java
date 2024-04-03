package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.*;

import java.util.Collection;

public interface IRepository<T extends Entitate> extends Iterable{
    public void add(T elem) throws  RepositoryException;

    public void remove(int id);

    public T find(int id);
    public void deleteAll();

    public Collection<T> getAll();

    public void update(T newElem);

    public T getPacientByID(int id);
}
