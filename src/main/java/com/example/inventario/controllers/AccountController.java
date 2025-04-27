package com.example.inventario.controllers;

import com.example.inventario.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService service;
    @GetMapping("/search/{id}")
    public ResponseEntity<?> find(@PathVariable int id){
        return service.findById(id);
    }
    @GetMapping("/findByClient/{id}")
    public ResponseEntity<?> findByClient(@PathVariable int id){
        return service.findByClientId(id);
    }

    @PostMapping("/save/{id}")
    public void save(@PathVariable int id){
        service.save(id);
    }

    @PutMapping("/addProduct/{idCli}/product/{idP}")
    public ResponseEntity<?> add(@PathVariable int idCli,@PathVariable int idP){
        return service.addProduct(idCli,idP);
    }

    @PutMapping("/pay/{mount}/client/{idCli}")
    public ResponseEntity<?> pay(@PathVariable double mount,@PathVariable int idCli){
        return service.pay(mount,idCli);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id);
    }

}
