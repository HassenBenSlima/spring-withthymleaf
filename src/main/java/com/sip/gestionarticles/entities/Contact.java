package com.sip.gestionarticles.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "email")
    private String email;


    @Column(name = "fax")
    private Long fax;

    @Column(name = "tel")
    private Long tel;

    public Contact() {
    }

    public Contact(long id, String email, Long fax, Long tel) {
        this.id = id;
        this.email = email;
        this.fax = fax;
        this.tel = tel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFax() {
        return fax;
    }

    public void setFax(Long fax) {
        this.fax = fax;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
}
