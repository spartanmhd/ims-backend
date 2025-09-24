package com.example.ims_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ProduitOrigineKey implements Serializable{

    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "id_origine")
    private Integer idOrigine;

    public ProduitOrigineKey() {}
    public ProduitOrigineKey(Long idProduit, Integer idOrigine) {
        this.idProduit = idProduit;
        this.idOrigine = idOrigine;
    }

    public Long getIdProduit() {
        return idProduit;
    }
    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }
    public Integer getIdOrigine() {
        return idOrigine;
    }
    public void setIdOrigine(Integer idOrigine) {
        this.idOrigine = idOrigine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProduitOrigineKey)) return false;
        ProduitOrigineKey that = (ProduitOrigineKey) o;
        return Objects.equals(idProduit, that.idProduit) &&
                Objects.equals(idOrigine, that.idOrigine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, idOrigine);
    }


}
