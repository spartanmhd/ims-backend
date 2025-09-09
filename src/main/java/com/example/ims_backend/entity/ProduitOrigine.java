package com.example.ims_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produit_origine")
@JsonIgnoreProperties({"produit"})

public class ProduitOrigine {
    @EmbeddedId
    private ProduitOrigineKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduit")
    @JoinColumn(name = "id_produit")
    @JsonIgnore
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idOrigine")
    @JoinColumn(name = "id_origine", insertable = false, updatable = false)
    private Origine origine;

    @NotNull(message = "La proportion pour l'origine ne doit pas être vide.")
    @Column(name = "proportion", nullable = false) // FIXED: no trailing space!
    @JsonProperty("proportion")
    private BigDecimal proportion; // should be 0 < proportion < 1

    public ProduitOrigine() {}

    public ProduitOrigine(ProduitOrigineKey id, Produit produit, Origine origine, BigDecimal proportion) {
        this.id = id;
        this.produit = produit;
        this.origine = origine;
        this.proportion = proportion;
    }

    public ProduitOrigineKey getId() {
        return id;
    }

    public void setId(ProduitOrigineKey id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Origine getOrigine() {
        return origine;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProduitOrigine)) return false;
        ProduitOrigine that = (ProduitOrigine) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(produit, that.produit) &&
                Objects.equals(origine, that.origine) &&
                Objects.equals(proportion, that.proportion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produit, origine, proportion);
    }
}