package com.example.ims_backend.dto;

import java.math.BigDecimal;

public class AchatOrigineDTO {
    private Integer origineId;
    private BigDecimal prixAchat;
    private BigDecimal quantite;

    // Getters and setters
    public Integer getOrigineId() { return origineId; }
    public void setOrigineId(Integer origineId) { this.origineId = origineId; }

    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }

    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }
}