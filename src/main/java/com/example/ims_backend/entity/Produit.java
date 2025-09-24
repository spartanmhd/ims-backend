package com.example.ims_backend.entity;



import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @NotBlank(message = "Le nom du produit ne doit pas être vide.")
    @Column(name = "nom")
    @NotNull
    private String nom;

//    @NotNull(message = "Le prix du produit ne doit pas être vide.")
    @Column(name = "prix_achat")
    private BigDecimal prixAchat;

    @Column(name = "emballage")
    private BigDecimal emballage;

    @Column(name = "autres")
    private BigDecimal autres;

//    @NotNull(message = "Le prix de vente ne doit pas être vide.")
    @Column(name = "prix_vente")
    private BigDecimal prixVente;

//    @NotNull(message = "La quantité du produit ne doit pas être vide.")
    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "marque")
    @NotNull(message = "La marque ne doit pas être vide")
    private String marque;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
//    @NotEmpty(message = "La liste des origines ne doit pas être vide.")
    @JsonProperty("origines")
    @JsonIgnore
    private List<ProduitOrigine> origines;

    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }

    public BigDecimal getEmballage() { return emballage; }
    public void setEmballage(BigDecimal emballage) { this.emballage = emballage; }

    public BigDecimal getAutres() { return autres; }
    public void setAutres(BigDecimal autres) { this.autres = autres; }

    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }

    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }

    public List<ProduitOrigine> getOrigines() {
        return origines;
    }
    public void setOrigines(List<ProduitOrigine> origines) {
        this.origines = origines;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }



}
