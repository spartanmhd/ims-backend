package com.example.ims_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "achat_origine")
public class AchatOrigine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_achat")
    @JsonIgnore
    private Achat achat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_origine")
    private Origine origine;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prixAchat;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantite;

    // Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Achat getAchat() { return achat; }
    public void setAchat(Achat achat) { this.achat = achat; }

    public Origine getOrigine() { return origine; }
    public void setOrigine(Origine origine) { this.origine = origine; }

    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }

    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }

    @Transient
    public BigDecimal getTotalTTC() {
        if (prixAchat == null || quantite == null) return BigDecimal.ZERO;
        return prixAchat.multiply(quantite);
    }

    @Transient
    public BigDecimal getPrixAchatHT() {
        if (prixAchat == null) return BigDecimal.ZERO;
        return prixAchat.divide(new BigDecimal("1.20"), 2, BigDecimal.ROUND_HALF_UP);
    }
}