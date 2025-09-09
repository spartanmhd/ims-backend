package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for creating, updating, and patching Produit.
 * All fields should be nullable to allow partial updates (PATCH).
 */
public class ProduitRequestDTO {
    private String nom;
    private String marque;
    private BigDecimal emballage;
    private BigDecimal autres;
    private BigDecimal quantite;
    private BigDecimal prixVente; // <-- Add this field to support patching sale price
    private List<ProduitOrigineDTO> origines;

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }

    public BigDecimal getEmballage() { return emballage; }
    public void setEmballage(BigDecimal emballage) { this.emballage = emballage; }

    public BigDecimal getAutres() { return autres; }
    public void setAutres(BigDecimal autres) { this.autres = autres; }

    public BigDecimal getQuantite() {
        return quantite;
    }
    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }
    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public List<ProduitOrigineDTO> getOrigines() {
        return origines;
    }
    public void setOrigines(List<ProduitOrigineDTO> origines) {
        this.origines = origines;
    }

    @Override
    public String toString() {
        return "ProduitRequestDTO [nom=" + nom + ", marque=" + marque + ", emballage=" + emballage +
                ", autres=" + autres + ", quantite=" + quantite + ", prixVente=" + prixVente +
                ", origines=" + origines + "]";
    }
}