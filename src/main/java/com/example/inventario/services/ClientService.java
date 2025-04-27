package com.example.inventario.services;

import com.example.inventario.entities.Client;
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

    public ResponseEntity<?> findById(Integer id) {
        Optional<Client> clientOpt = repository.findById(id);
        if (clientOpt.isPresent()) {
            return ResponseEntity.ok(clientOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
    }

    public List<Client> findAll() {
        return (List<Client>) repository.findAll();
    }

    public void save(Client client) {
        repository.save(client);
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
        if (repository.existsById(client.getId())){
            repository.save(client);
            return ResponseEntity.ok("Cliente Actualizado exitosamente");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no se encontro");
        }
    }

}
