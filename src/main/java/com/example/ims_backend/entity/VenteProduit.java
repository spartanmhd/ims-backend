package com.example.ims_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Line item for a Vente. Holds product, quantities and prices.
 */
@Entity
@Table(name = "vente_produit")
public class VenteProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vente_id")
    private Vente vente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id_produit")
    private Produit produit;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "prix_vente", precision = 19, scale = 2)
    private BigDecimal prixVente;

    @Column(name = "prix_achat", precision = 19, scale = 2)
    private BigDecimal prixAchat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vente getVente() { return vente; }
    public void setVente(Vente vente) { this.vente = vente; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    public BigDecimal getPrixAchat() { return prixAchat; }
    public void setPrixAchat(BigDecimal prixAchat) { this.prixAchat = prixAchat; }
}
