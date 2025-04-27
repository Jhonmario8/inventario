package com.example.inventario.repositories;

import com.example.inventario.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query("SELECT p from Product p where p.name like %:name%")
    List<Product> findContainsName(@Param("name") String name);
}
