package com.example.ims_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "origine", uniqueConstraints = @UniqueConstraint(columnNames = "nom"))
public class Origine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrigine;

    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne doit pas être vide")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    @NotNull(message = "Le fournisseur est obligatoire")
    private Fournisseur fournisseur;

    @Column(precision = 14, scale = 2)
    @NotNull(message = "Le prix d'achat est obligatoire")
    private BigDecimal prixAchat; //TTC

    @Column(precision = 14, scale = 2)
    @NotNull(message = "La quantité est obligatoire")
    private BigDecimal quantite;

    @AssertTrue(message = "Le nom de l'origine ne doit pas être vide")
    private boolean isNomValid() {
        return nom != null && !nom.trim().isEmpty();
    }

    @AssertTrue(message = "Le fournisseur de l'origine est obligatoire")
    private boolean isFournisseurValid() {
        return fournisseur != null;
    }

    @AssertTrue(message = "Le prix d'achat de l'origine est obligatoire")
    private boolean isPrixAchatValid() {
        return prixAchat != null;
    }

    @AssertTrue(message = "La quantité de l'origine est obligatoire")
    private boolean isQuantiteValid() {
        return quantite != null;
    }

    @Transient
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
    public Double getTotalPrixHT() {
        if (prixAchat == null || quantite == null) return null;
        return round(prixAchat.divide(new BigDecimal("1.20"), 2, BigDecimal.ROUND_HALF_UP).multiply(quantite).doubleValue());
    }

    private Double round(Double value) {
        if (value == null) return null;
        return Math.round(value * 100.0) / 100.0;
    }

    // Getters and Setters
    public Long getIdOrigine() { return idOrigine; }
    public void setIdOrigine(Long idOrigine) { this.idOrigine = idOrigine; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }

    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }

}