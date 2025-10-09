package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Read model for Vente with its lines
 */
public class VenteDTO {
    private Long id;
    private Integer clientId;
    private String clientNom;
    private LocalDate datePaiement;
    private String modePaiement;
    private String numeroCheque;
    private BigDecimal avance;
    private LocalDate dateLivraison;
    private String status; // "payé" | "non payé"
    private List<VenteProduitDTO> produits;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
    public String getNumeroCheque() { return numeroCheque; }
    public void setNumeroCheque(String numeroCheque) { this.numeroCheque = numeroCheque; }
    public BigDecimal getAvance() { return avance; }
    public void setAvance(BigDecimal avance) { this.avance = avance; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<VenteProduitDTO> getProduits() { return produits; }
    public void setProduits(List<VenteProduitDTO> produits) { this.produits = produits; }
}
