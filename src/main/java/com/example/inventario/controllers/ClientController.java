package com.example.inventario.controllers;

import com.example.inventario.entities.Client;
import com.example.inventario.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/showAll")
    public List<Client> showAll(){
        return service.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody Client client){
        service.save(client);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Client client){
       return service.update(client);
    }
    @GetMapping("/search")
    public ResponseEntity<?> find(int id){
       return service.findById(id);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(int id){
        return service.delete(id);
    }
}
