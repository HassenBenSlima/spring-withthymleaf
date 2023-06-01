package com.sip.gestionarticles.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagingRepository  extends CrudRepository<Messaging, Long> {
	/*@Query("FROM Article a WHERE a.inventoryQuantity = 0")
    List<Article> findProductSoldOut();*/
	
	@Query("FROM Messaging m WHERE m.recipient = ?1")
	List<Messaging> findAllMessageOfCurrentUser(User user);
	
	@Query("FROM Messaging m WHERE m.status = ?1 and m.recipient = ?2")
	List<Messaging> findAllMessageByStatus(String status,User user);
}
