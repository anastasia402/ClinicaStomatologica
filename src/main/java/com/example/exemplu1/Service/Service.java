package com.example.exemplu1.Service;

import com.example.exemplu1.Domain.*;
import com.example.exemplu1.Repository.*;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Service<T extends Entitate>{
    private IRepository<T> repo;

    public Service(IRepository<T> repo) {
        this.repo = repo;
    }

    public void add(T elem) throws Exception {
        try {
            this.repo.add(elem);
        } catch (DuplicateObjectException e){
            throw new Exception(e.getMessage());
        }

    }

    public void remove(int id){
        int cnt = 0;
        for(T elem: this.repo.getAll()){
            if (elem.getID() == id){
                cnt++;
            }
        }
        if (cnt==0){
            throw new RuntimeException("Nu exista entitate cu acest id");
        }
        this.repo.remove(id);
    }

    public void update(T newElem){
        try {
            this.repo.update(newElem);
        } catch (IllegalArgumentException e){
            throw new RuntimeException();
        }

    }

    public T find(int id){
        return this.repo.find(id);
    }

    public Collection<T> getAll(){
        return this.repo.getAll();
    }

    public void deleteAll() {
        this.repo.getAll().clear();
    }

    public T getPacientByID(int id){
        return this.repo.getPacientByID(id);
    }

}
