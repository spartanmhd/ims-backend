package com.example.ims_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idFournisseur;

    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "ICE est requis")
    private String ice;

    @NotBlank(message = "Téléphone est requis")
    private String tel;

    @NotBlank(message = "Ville est requise")
    private String ville;

    @NotBlank(message = "Adresse est requise")
    private String adresse;

    // Getters and setters
    public Integer getIdFournisseur() { return idFournisseur; }
    public void setIdFournisseur(Integer idFournisseur) { this.idFournisseur = idFournisseur; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIce() { return ice; }
    public void setIce(String ice) { this.ice = ice; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

}
