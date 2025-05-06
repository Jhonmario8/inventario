package com.example.inventario.services;

import com.example.inventario.entities.Client;
import com.example.inventario.repositories.AccountRepository;
import com.example.inventario.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private AccountService accountService;

    public ResponseEntity<?> findById(Integer id) {
        Optional<Client> clientOpt = repository.findById(id);
        if (clientOpt.isPresent()) {
            Client client=clientOpt.get();
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
    }
    public ResponseEntity<?> findByName(String name){
        Optional<Client> clientOpt=repository.findByName(name);

        if (clientOpt.isPresent()) {
            return ResponseEntity.ok(clientOpt.get());
        }else{
            System.out.println("No se encontro");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
    }
    public List<Client> findAll() {
        return (List<Client>) repository.findAll();
    }

    public void save(Client client) {
        repository.save(client);
        accountService.save(client.getId());

    }

    public ResponseEntity<?> delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Cliete eliminado exitosamente!!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no se encontro");
        }
    }

    public ResponseEntity<?> update(Client client){
        Optional<Client> clientOpt=repository.findById(client.getId());
        if (clientOpt.isPresent()){
            Client cli=clientOpt.get();
            cli.setName(client.getName());
            cli.setPhone(client.getPhone());
            cli.setAge(client.getAge());
            repository.save(cli);
            return ResponseEntity.ok("Cliente Actualizado exitosamente");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no se encontro");
        }
    }

    public List<Client> findByNameContaining(String name){
        return (List<Client>) repository.findClientByNameContaining(name);
    }
}
