package com.example.ims_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Integer idClient;

    @Column(name = "type", length = 20, nullable = false)
    @NotBlank(message = "Le type est requis (client ou vendeur)")
    private String type; // values: "client" or "vendeur"

    @Column(name = "nom", length = 100, nullable = false)
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @Column(name = "cin", length = 50)
    private String cin;

    @Column(name = "ice", length = 50)
    private String ice;

    @Column(name = "adresse", length = 255)
    private String adresse;

    @Column(name = "ville", length = 100)
    private String ville;

    @Column(name = "voiture", length = 100)
    private String voiture;

    @Column(name = "immatriculation", length = 50)
    private String immatriculation;

    @Column(name = "n_permis", length = 50)
    private String nPermis;

    @Column(name = "tel", length = 50)
    private String tel;

    // Getters and setters
    public Integer getIdClient() { return idClient; }
    public void setIdClient(Integer idClient) { this.idClient = idClient; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getIce() { return ice; }
    public void setIce(String ice) { this.ice = ice; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getVoiture() { return voiture; }
    public void setVoiture(String voiture) { this.voiture = voiture; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public String getnPermis() { return nPermis; }
    public void setnPermis(String nPermis) { this.nPermis = nPermis; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
}
