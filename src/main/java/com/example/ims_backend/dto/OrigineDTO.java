package com.example.ims_backend.dto;

public class OrigineDTO {
    private Integer idOrigine;
    private String nom;
    private Integer idFournisseur;
    private String fournisseurName; // Optional, for display purposes
    private Integer stock;

    public Integer getIdOrigine() { return idOrigine; }
    public void setIdOrigine(Integer idOrigine) { this.idOrigine = idOrigine; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Integer getIdFournisseur() { return idFournisseur; }
    public void setIdFournisseur(Integer idFournisseur) { this.idFournisseur = idFournisseur; }

    public String getFournisseurName() { return fournisseurName; }
    public void setFournisseurName(String fournisseurName) { this.fournisseurName = fournisseurName; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}