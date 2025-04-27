package com.example.inventario.services;

import com.example.inventario.entities.Product;
import com.example.inventario.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public ResponseEntity<?> findById(Integer id){
        Optional<Product> productOpt=repository.findById(id);
        if (productOpt.isPresent()){
            return ResponseEntity.ok(productOpt.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto");
        }
    }
    public List<Product> findByName(String name){
        return repository.findContainsName(name);
    }

    public void save(Product product){
        repository.save(product);
    }
    public ResponseEntity<?> update(Product product){
        if (repository.existsById(product.getCode())){
            repository.save(product);
            return ResponseEntity.ok("Producto acutalizado con exito");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto");
        }
    }
    public ResponseEntity<?> delete(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto no se encontro");
        }
    }
}
