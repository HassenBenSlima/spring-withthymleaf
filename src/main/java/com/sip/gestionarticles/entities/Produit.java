package com.sip.gestionarticles.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "label")
    private String label;

    @Column(name = "photoFace")
    private String photoFace;

    @Column(name = "photoProfil")
    private String photoProfil;

    @Column(name = "prix")
    private float prix;

    @Column(name = "description")
    private String description;
    @Column(name = "quantiteStock")
    private Integer quantiteStock;
    @Column(name = "prixPromotion")
    private Double prixPromotion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateExpiration")
    private Date dateExpiration;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Provider provider;


    public Produit() {
    }

    public Produit(long id, String label, String photoFace, String photoProfil, float prix, String description, Integer quantiteStock, Double prixPromotion, Date dateExpiration, Provider provider) {
        this.id = id;
        this.label = label;
        this.photoFace = photoFace;
        this.photoProfil = photoProfil;
        this.prix = prix;
        this.description = description;
        this.quantiteStock = quantiteStock;
        this.prixPromotion = prixPromotion;
        this.dateExpiration = dateExpiration;
        this.provider = provider;
    }

    public String getPhotoFace() {
        return photoFace;
    }

    public void setPhotoFace(String photoFace) {
        this.photoFace = photoFace;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


    public Integer getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public Double getPrixPromotion() {
        return prixPromotion;
    }

    public void setPrixPromotion(Double prixPromotion) {
        this.prixPromotion = prixPromotion;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
