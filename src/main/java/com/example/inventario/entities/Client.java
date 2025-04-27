package com.example.inventario.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    private int id;
    private String name;
    private Long phone;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
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
