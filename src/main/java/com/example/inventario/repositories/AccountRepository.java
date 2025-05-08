package com.example.inventario.repositories;

import com.example.inventario.entities.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AccountRepository extends CrudRepository<Account,Integer> {

    @Query("SELECT a FROM Account a where a.client.id=:idClient")
    Optional<Account> findByClientId(@Param("idClient") Integer idClient);

    @Query(value = """
SELECT quantity from account_product where id_account=:accountId and id_product=:productId
""",nativeQuery = true)
    Integer getQuantity(@Param("accountId") Integer accountId,@Param("productId") Integer productId);

    @Modifying
    @Query(value = """
    INSERT INTO account_product(id_account, id_product, quantity)
    VALUES (:accountId, :productId, :quantity)
    ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
    """, nativeQuery = true)
    void addProduct(
            @Param("accountId") Integer accountId,
            @Param("productId") Integer productId,
            @Param("quantity") int quantity
    );

}
