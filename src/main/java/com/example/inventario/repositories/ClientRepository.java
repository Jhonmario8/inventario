package com.example.inventario.repositories;

import com.example.inventario.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Integer> {


    Optional<Client> findByName(String name);
}
