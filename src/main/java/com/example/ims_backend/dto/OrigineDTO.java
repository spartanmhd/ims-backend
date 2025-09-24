package com.example.ims_backend.dto;

public class OrigineDTO {
    private Integer idOrigine;
    private String nom;
    private Integer idFournisseur;

    public Integer getIdOrigine() {
        return idOrigine;
    }

    public void setIdOrigine(Integer idOrigine) {
        this.idOrigine = idOrigine;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }
}
