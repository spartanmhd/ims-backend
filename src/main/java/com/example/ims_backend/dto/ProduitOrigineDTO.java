package com.example.ims_backend.dto;

import java.math.BigDecimal;

public class ProduitOrigineDTO {
    private Integer idOrigine;
    private String origineNom;
    private BigDecimal proportion;

    public Integer getIdOrigine() { return idOrigine; }
    public void setIdOrigine(Integer idOrigine) { this.idOrigine = idOrigine; }
    public String getOrigineNom() {
        return origineNom;
    }
    public void setOrigineNom(String origineNom) {
        this.origineNom = origineNom;
    }
    public BigDecimal getProportion() {
        return proportion;
    }
    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}
