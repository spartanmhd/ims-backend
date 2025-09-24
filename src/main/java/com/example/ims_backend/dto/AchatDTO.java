package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AchatDTO {
    private Integer idAchat;
    private Integer fournisseurId;
    private String fournisseurNom; // optional, for display
    private LocalDate dateAchat;
    private String numeroBL;
    private String modePaiement;
    private String numeroCheque;
    private LocalDate datePaiement;
    private BigDecimal avance;
    private List<AchatOrigineDTO> origines;
    private BigDecimal totalPrixTTC;

    // Getters and Setters

    public Integer getIdAchat() {
        return idAchat;
    }
    public void setIdAchat(Integer idAchat) {
        this.idAchat = idAchat;
    }

    public Integer getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Integer fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getFournisseurNom() {
        return fournisseurNom;
    }
    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }
    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getNumeroBL() {
        return numeroBL;
    }
    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    public String getModePaiement() {
        return modePaiement;
    }
    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }
    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }
    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getAvance() {
        return avance;
    }
    public void setAvance(BigDecimal avance) {
        this.avance = avance;
    }

    public List<AchatOrigineDTO> getOrigines() {
        return origines;
    }
    public void setOrigines(List<AchatOrigineDTO> origines) {
        this.origines = origines;
    }

    public BigDecimal getTotalPrixTTC() {
        return totalPrixTTC;
    }
    public void setTotalPrixTTC(BigDecimal totalPrixTTC) {
        this.totalPrixTTC = totalPrixTTC;
    }
}