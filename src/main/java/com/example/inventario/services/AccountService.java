package com.example.inventario.services;

import com.example.inventario.entities.Account;
import com.example.inventario.entities.AddProdutctDto;
import com.example.inventario.entities.Client;
import com.example.inventario.entities.Product;
import com.example.inventario.repositories.AccountRepository;
import com.example.inventario.repositories.ClientRepository;
import com.example.inventario.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> findById(Integer id) {
        Optional<Account> optionalAccount = repository.findById(id);
        if (optionalAccount.isPresent()) {
            return ResponseEntity.ok(optionalAccount.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cuenta no se encontro");
        }
    }

    public ResponseEntity<?> findByClientId(Integer id) {
        Optional<Account> accountOpt = repository.findByClientId(id);
        if (accountOpt.isPresent()) {
            return ResponseEntity.ok(accountOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cuenta no se encontro");
        }
    }



    public void save(Integer id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            Account ac = new Account();
            ac.setClient(clientOpt.get());
            repository.save(ac);
        }
    }

    public ResponseEntity<?> addProduct(AddProdutctDto request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        Optional<Account> accountOpt = repository.findByClientId(request.getIdCliente());

        if (productOpt.isPresent() && accountOpt.isPresent()) {
            Account ac = accountOpt.get();
            Product p = productOpt.get();
            ac.getProducts().add(p);
            p.getAccounts().add(ac);
            p.setStock(p.getStock()-request.getStock());
            ac.setMount(ac.getMount() + p.getPrice());
            repository.save(ac);
            return ResponseEntity.ok("Producto asignado con exito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto o la cuenta no existen");

        }
    }

    public ResponseEntity<?> pay(Double mount, Integer id) {
        Optional<Account> accountOpt = repository.findByClientId(id);
        if (accountOpt.isPresent()) {
            Account ac = accountOpt.get();
            if (ac.getMount() <= mount) {
                if (ac.getProducts() != null) {
                    for (Product p : ac.getProducts()) {
                        p.getAccounts().remove(ac);
                    }
                    ac.getProducts().clear();
                }
                double returned = mount - ac.getMount();
                ac.setMount(0);
                repository.save(ac);
                return ResponseEntity.ok("Su cambio es: " + String.format("%.2f", returned));
            } else {
                ac.setMount(ac.getMount() - mount);
                repository.save(ac);
                return ResponseEntity.ok("Credito: " + String.format("%.2f", ac.getMount()));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cuenta no se encontro");
    }

    public ResponseEntity<?> delete(Integer id){
        Optional<Account> accountOpt=repository.findByClientId(id);
        if (accountOpt.isPresent()){
            repository.delete(accountOpt.get());
            return ResponseEntity.ok("Cuenta eliminada con exito");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cuenta no se encontro");
        }
    }


}
