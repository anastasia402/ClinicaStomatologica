package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.*;

import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entitate> extends Repository<T>{
    private String fileName;

    public BinaryFileRepository(String fileName) throws FileNotFoundException, ClassNotFoundException {
        this.fileName = fileName;
        this.entities.clear();
        loadFromFile();
    }

    public List<T> getAll(){
        return this.entities;
    }

    @Override
    public void add(T elem) throws DuplicateObjectException {
        super.add(elem);
        saveFile();
    }

    @Override
    public void remove(int id){
        super.remove(id);
        saveFile();
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                entities = (List<T>) obj;
            }
        } catch (EOFException e) {
            // Handle end of file
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }


    public void saveFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            // Handle the exception, e.g., by logging or rethrowing it
            e.printStackTrace(); // Replace with proper error handling
        }
    }
}
