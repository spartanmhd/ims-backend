package com.example.ims_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO to create a vente with multiple products
 */
public class VenteCreateRequestDTO {
    private Integer clientId;
    private LocalDate datePaiement;
    private String modePaiement; // if "espèce", avance is required
    private String numeroCheque; // only applicable if modePaiement is "cheque" or "chèque"
    private BigDecimal avance; // nullable otherwise
    private LocalDate dateLivraison;
    private String status; // "payé" | "non payé"
    private List<Item> items;

    public static class Item {
        private Long productId;
        private Integer quantity;
        private BigDecimal prixVente;
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getPrixVente() { return prixVente; }
        public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    }

    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
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
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
