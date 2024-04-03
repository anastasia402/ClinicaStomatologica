package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.*;

import java.io.*;
import java.util.Collection;

public class TextFileRepository<T extends Entitate> extends Repository<T>{
    private String fileName;
    private Converter<T> converter;

    public TextFileRepository(String fileName, Converter<T> converter) {
        this.fileName = fileName;
        this.converter = converter;
        loadFile();
    }
    public void loadFile(){

        entities.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line!=null && !line.isEmpty()){
                entities.add(converter.fromStringConverter(line));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(){
        try(FileWriter fw = new FileWriter(fileName)) {
            for (T object:entities){
                fw.write(converter.toStringConverter(object));
                fw.write("\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<T> getAll() {
        return super.getAll();
    }

//    public void scrie(){
//        List<T> lisa = new ArrayList<>();
//        for (T obj: entities){
//            if (obj.isConformant())
//                lisa.add(obj);
//        }
//        lisa.sort(Comparator.comparing(T::getAuthor));
//        try(FileWriter fw = new FileWriter(fileName)){
//            for (T soft: lisa){
//                fw.write(converter.toStringConverter(soft));
//                fw.write("\r\n");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void add(T elem) throws DuplicateObjectException {
        super.add(elem);
        saveToFile();
    }


    @Override
    public void remove(int id) {
        super.remove(id);
        saveToFile();
    }
}
