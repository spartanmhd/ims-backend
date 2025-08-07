package com.example.ims_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFournisseur;

    private String ice;
    private String tel;
    private String ville;
    private String adresse;

    // Getters and setters
    public Integer getIdFournisseur() { return idFournisseur; }
    public void setIdFournisseur(Integer idFournisseur) { this.idFournisseur = idFournisseur; }

    public String getIce() { return ice; }
    public void setIce(String ice) { this.ice = ice; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

}
