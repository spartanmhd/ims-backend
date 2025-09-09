package com.example.ims_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "achat")
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAchat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idFournisseur", referencedColumnName = "idFournisseur", nullable = false)
    private Fournisseur fournisseur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idOrigine", referencedColumnName = "idOrigine", nullable = false)
    @NotNull(message = "L'origine est obligatoire")
    private Origine origine;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Le prix d'achat est obligatoire")
    private BigDecimal prixAchat; //TTC

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "La quantité est obligatoire")
    private BigDecimal quantite;

    @Column(nullable = false)
    @NotNull(message = "La date d'achat est obligatoire")
    private LocalDate dateAchat;

    @Column(name = "numero_bl", nullable = false)
    @NotBlank(message = "Le numéro BL est obligatoire")
    private String numeroBL;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Le mode de paiement est obligatoire")
    private String modePaiement;

    @Column(length = 50)
    private String numeroCheque; // Only required if modePaiement == "Chèque"

    @Column(nullable = false)
    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDate datePaiement;

    @Column(precision = 12, scale = 2)
    private BigDecimal avance;


    @AssertTrue(message = "Le numéro de chèque est obligatoire si le mode de paiement est Chèque")
    public boolean isChequeNumberValid() {
        if ("Chèque".equalsIgnoreCase(modePaiement)) {
            return numeroCheque != null && !numeroCheque.trim().isEmpty();
        }
        return true;
    }
    @Transient
    @JsonProperty("prixAchatHT")
    public Double getPrixAchatHT() {
        if (prixAchat == null) return null;
        return round(prixAchat.divide(new BigDecimal("1.20"), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Transient
    public Double getTotalPrixTTC() {
        if (prixAchat == null || quantite == null) return null;
        return round(prixAchat.multiply(quantite).doubleValue());
    }

    @Transient
    public Double getRestToPay() {
        if (prixAchat == null || quantite == null) return null;
        BigDecimal total = prixAchat.multiply(quantite);
        BigDecimal avanceValue = avance != null ? avance : BigDecimal.ZERO;
        return round(total.subtract(avanceValue).doubleValue());
    }

    private Double round(Double value) {
        if (value == null) return null;
        return Math.round(value * 100.0) / 100.0;
    }

    // Getters and Setters
    public Integer getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(Integer idAchat) {
        this.idAchat = idAchat;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Origine getOrigine() {
        return origine;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
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
}