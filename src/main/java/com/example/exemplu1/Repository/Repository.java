package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Repository<T extends Entitate> implements IRepository<T> {
    public List<T> entities = new ArrayList<T>();
    @Override
    public void add(T entity) throws DuplicateObjectException {
        if (entity == null)
        {
            throw new IllegalArgumentException("Entity can't be null!");
        }
        if (find(entity.getID()) != null) {
            throw new DuplicateObjectException("Entity already exists!");
        }
        entities.add(entity);
    }

    @Override
    public void update(T entity) {
        for (int i = 0; i <entities.size(); i++)
        {
            if (entities.get(i).getID() == entity.getID())
            {
                entities.set(i, entity);
            }
        }
    }

    @Override
    public void remove(int id) {
        for (T entity :  entities)
        {
            if (entity.getID() == id)
            {
                entities.remove(entity);
                return;
            }
        }
    }

    @Override
    public void deleteAll() {
        this.entities.clear();
    }

    @Override
    public T find(int id) {
        for (T entity :  entities)
        {
            if (entity.getID() == id)
            {
                return entity;
            }
        }
        return null;
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<T>(entities);
    }

    public T getPacientByID(int id){
        for (T elem: this.entities){
            if (elem.getID() == id)
                return elem;
        }

        throw new IllegalArgumentException("Entity cannot be found");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entities).iterator();
    }
}
