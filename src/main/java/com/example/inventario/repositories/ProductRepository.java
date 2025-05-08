package com.example.inventario.repositories;

import com.example.inventario.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% ORDER BY p.name")
    List<Product> findContainsName(@Param("name") String name);
    void deleteByName(String name);
    Optional<Product> findByName(@Param("name") String name);



}
