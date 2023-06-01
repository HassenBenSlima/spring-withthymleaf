package com.sip.gestionarticles.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Provider {
	@Id  //clé primaire
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "logo")
    private String logo;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable=false)
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;


    @Column(name = "telephone", nullable=false)
    private Integer telephone;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address", nullable=false)
    private String address;


    @OneToMany(mappedBy="provider")
    private List<Produit> listProduits;

    public Provider() {
    	System.out.println("Hello From constructeur");	
    }


    public Provider(long id, String logo, String name, String email, Integer telephone, String address, List<Produit> listProduits, List<Produit> articles) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.listProduits = listProduits;
        this.articles = articles;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }

    @Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
	}

	public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    // block d'initilisatiin d'instance
    {
    	System.out.println("Hello From Block d'instance");
    }
    
    // block d'initilisatiin d'instance
    static {
    	System.out.println("Hello From Block de classe");
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "provider")
    private List<Produit> articles;

    public List<Produit> getArticles() {
        return articles;
    }

    public void setArticles(List<Produit> articles) {
        this.articles = articles;
    }


}

