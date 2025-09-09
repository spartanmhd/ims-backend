package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProduitResponseDTO {
    private Long idProduit;
    private String nom;
    private String marque;
    private BigDecimal emballage;
    private BigDecimal autres;
    private BigDecimal quantite;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private List<ProduitOrigineDTO> origines;

    // Getters and Setters
    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public BigDecimal getEmballage() { return emballage; }
    public void setEmballage(BigDecimal emballage) { this.emballage = emballage; }

    public BigDecimal getAutres() { return autres; }
    public void setAutres(BigDecimal autres) { this.autres = autres; }

    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }
    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }
    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    public List<ProduitOrigineDTO> getOrigines() { return origines; }
    public void setOrigines(List<ProduitOrigineDTO> origines) { this.origines = origines; }
}