package com.example.inventario.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {
    @Id
    private int id;
    private String name;
    private int age;
    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL)
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
