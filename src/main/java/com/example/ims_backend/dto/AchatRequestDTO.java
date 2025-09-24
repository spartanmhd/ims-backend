package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AchatRequestDTO {
    private Integer fournisseurId;
    private String numeroBL;
    private String modePaiement;
    private String numeroCheque;
    private LocalDate dateAchat;
    private LocalDate datePaiement;
    private BigDecimal avance;
    private List<AchatOrigineDTO> origines;

    public Integer getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Integer fournisseurId) {
        this.fournisseurId = fournisseurId;
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
    public LocalDate getDateAchat() {
        return dateAchat;
    }
    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
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
}
