package com.example.ims_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "achat")
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAchat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idFournisseur", referencedColumnName = "idFournisseur", nullable = false)
    private Fournisseur fournisseur;

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

    // Legacy column present in some databases: make it nullable and ignored by JPA writes
    @Column(name = "id_origine", insertable = false, updatable = false, nullable = true)
    private Integer legacyIdOrigine;

    @OneToMany(mappedBy = "achat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AchatOrigine> achatOrigines;

    // Getters and Setters

    public Integer getIdAchat() { return idAchat; }
    public void setIdAchat(Integer idAchat) { this.idAchat = idAchat; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public LocalDate getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDate dateAchat) { this.dateAchat = dateAchat; }

    public String getNumeroBL() { return numeroBL; }
    public void setNumeroBL(String numeroBL) { this.numeroBL = numeroBL; }

    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }

    public String getNumeroCheque() { return numeroCheque; }
    public void setNumeroCheque(String numeroCheque) { this.numeroCheque = numeroCheque; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public BigDecimal getAvance() { return avance; }
    public void setAvance(BigDecimal avance) { this.avance = avance; }

    public List<AchatOrigine> getAchatOrigines() {
        return achatOrigines;
    }
    public void setAchatOrigines(List<AchatOrigine> achatOrigines) { this.achatOrigines = achatOrigines; }

    public Integer getLegacyIdOrigine() { return legacyIdOrigine; }
    public void setLegacyIdOrigine(Integer legacyIdOrigine) { this.legacyIdOrigine = legacyIdOrigine; }

    @Transient
    public BigDecimal getTotalPrixTTC() {
        if (achatOrigines == null || achatOrigines.isEmpty()) return BigDecimal.ZERO;
        return achatOrigines.stream()
                .map(AchatOrigine::getTotalTTC)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}