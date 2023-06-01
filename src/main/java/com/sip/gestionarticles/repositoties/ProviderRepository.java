package com.sip.gestionarticles.repositoties;

import com.sip.gestionarticles.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

//    @Query("FROM Article a WHERE a.provider.id = ?1")
    List<Provider> findProvidersByNameContaining(String name);



}
