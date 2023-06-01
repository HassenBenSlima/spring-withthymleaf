package com.sip.gestionarticles.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Messaging {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Object is mandatory")
    @Column(name = "object", nullable=false)
    private String object;
    
    @NotBlank(message = "Message is mandatory")
    @Column(name = "message", nullable=false)
    private String message;

    
    @NotBlank(message = "Status is mandatory")
    @Column(name = "status", nullable=false)
    private String status;
    
    //@NotBlank(message = "Sent date is mandatory")
    @Column(name = "sentDate", nullable=true)
    private LocalDate sentDate;
    
    
    public LocalDate getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDate localDate) {
		this.sentDate = localDate;
	}



	/**** Many To One ****/
    @JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User recipient;

    /**** Many To One ****/
    @JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;
    
    public Messaging() {}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getObject() {
		return object;
	}



	public void setObject(String object) {
		this.object = object;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public User getSender() {
		return sender;
	}



	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	public User getRecipient() {
		return recipient;
	}



	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}



	@Override
	public String toString() {
		return "Messaging [id=" + id + ", object=" + object + ", message=" + message + ", sender=" + sender
				+ ", recipient=" + recipient +  "]";
	}
	
	
	
    
}
