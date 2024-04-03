package com.example.exemplu1.Domain;

import java.io.Serializable;

public class Pacient extends Entitate implements Serializable {
    private String first_name;
    private String second_name;
    private int age;
    private static final long serialVersionUID = 4678425753399068306L;


    public Pacient(int ID, String first_name, String second_name, int age){
        super(ID);
        this.first_name = first_name;
        this.second_name = second_name;
        this.age = age;
    }

    //public Pacient(int id) {
        //super(id);
    //}

    @Override
    public String toString() {
        return "Pacient{" +
                "first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", age=" + age +
                ", ID=" + ID +
                '}';
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public int getAge() {
        return age;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
