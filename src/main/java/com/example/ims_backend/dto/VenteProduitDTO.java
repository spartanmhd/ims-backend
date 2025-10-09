package com.example.ims_backend.dto;

import java.math.BigDecimal;

public class VenteProduitDTO {
    private Long id;
    private Long productId;
    private String productNom;
    private Integer quantity;
    private BigDecimal prixVente;
    private BigDecimal prixAchat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductNom() { return productNom; }
    public void setProductNom(String productNom) { this.productNom = productNom; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }
}
