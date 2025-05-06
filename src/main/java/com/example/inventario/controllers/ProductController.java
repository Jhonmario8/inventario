package com.example.inventario.controllers;

import com.example.inventario.entities.Product;
import com.example.inventario.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/search/{id}")
    public ResponseEntity<?> find(@PathVariable int id){
        return service.findById(id);
    }
    @PostMapping("/save")
    public void save(@RequestBody Product product){
        service.save(product);
    }
    @PostMapping("/update")
    public void update(@RequestBody Product product){
         service.update(product);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id);
    }
    @GetMapping("/findByName/{name}")
    public List<Product> findByName(@PathVariable String name){
        return service.findByName(name);
    }
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<?> search(@PathVariable String name){
        return service.searchByName(name);
    }
    @GetMapping("/showAll")
    public List<Product> showAll(){
        return service.findAll();
    }
    @DeleteMapping("/deleteByNama/{name}")
    public void deleteByName(@PathVariable String name){
        service.deleteByName(name);
    }
}
