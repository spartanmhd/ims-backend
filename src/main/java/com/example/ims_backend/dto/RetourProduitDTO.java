package com.example.ims_backend.dto;

import java.time.LocalDate;

public class RetourProduitDTO {
    private Long id;
    private Long venteProduitId;
    private Integer quantityReturned;
    private LocalDate dateRetour;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVenteProduitId() { return venteProduitId; }
    public void setVenteProduitId(Long venteProduitId) { this.venteProduitId = venteProduitId; }
    public Integer getQuantityReturned() { return quantityReturned; }
    public void setQuantityReturned(Integer quantityReturned) { this.quantityReturned = quantityReturned; }
    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
}
