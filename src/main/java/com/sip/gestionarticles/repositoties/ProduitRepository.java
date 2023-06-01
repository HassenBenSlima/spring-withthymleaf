package com.sip.gestionarticles.repositoties;

import com.sip.gestionarticles.entities.Produit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProduitRepository extends CrudRepository<Produit, Long> {

//    @Query("FROM Produit a WHERE a.inventoryQuantity = 0")
//    List<Produit> findProductSoldOut();
    List<Produit> findProduitByProvider_Id(Long idProvider);

    List<Produit> findProduitByLabelContains(String label);


}
