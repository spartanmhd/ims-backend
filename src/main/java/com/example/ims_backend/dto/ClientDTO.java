package com.example.ims_backend.dto;

public class ClientDTO {
    private Integer idClient;
    private String type; // "client" or "vendeur"
    private String nom;
    private String cin;
    private String ice;
    private String adresse;
    private String ville;
    private String voiture;
    private String immatriculation;
    private String nPermis;
    private String tel;

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
