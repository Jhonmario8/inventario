package com.example.inventario.repositories;

import com.example.inventario.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AccountRepository extends CrudRepository<Account,Integer> {
    @Query("SELECT a FROM Account a where a.client.id=:idClient")
    Optional<Account> findByClientId(@Param("idClient") Integer idClient);
}
