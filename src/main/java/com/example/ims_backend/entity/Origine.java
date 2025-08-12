package com.example.ims_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Origine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idOrigine;

    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne doit pas être vide")
    private String nom;
    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    @NotNull(message = "Le fournisseur est obligatoire")
    private Fournisseur fournisseur;
    private Double prixAchat;
    private Double quantite;


    public Long getIdOrigine() { return idOrigine; }
    public void setIdOrigine(Long idOrigine) { this.idOrigine = idOrigine; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public Double getPrixAchat() { return prixAchat; }
    public void setPrixAchat(Double prixAchat) { this.prixAchat = prixAchat; }

    public Double getQuantite() { return quantite; }
    public void setQuantite(Double quantite) { this.quantite = quantite; }

}
