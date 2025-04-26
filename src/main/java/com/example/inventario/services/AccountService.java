package com.example.inventario.services;

import com.example.inventario.entities.Account;
import com.example.inventario.entities.Product;
import com.example.inventario.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public  Account findById(Integer id){
        return repository.findById(id).orElse(null);
    }
    public Account findByClientId(Integer id){
        return repository.findByClientId(id).orElse(null);
    }
    public double getMount(Integer id){
        Optional<Account> accountOpt=repository.findByClientId(id);
        return accountOpt.map(account -> account.getProducts().stream()
                .mapToDouble(Product::getPrice).sum()).orElse(0.0);
    }

}
