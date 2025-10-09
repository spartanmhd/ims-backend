package com.example.ims_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * RetourProduit tracks returns for a specific vente_produit line.
 */
@Entity
@Table(name = "retour_produit")
public class RetourProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vente_produit_id")
    private VenteProduit venteProduit;

    @Column(name = "quantity_returned")
    private Integer quantityReturned;

    @Column(name = "date_retour")
    private LocalDate dateRetour;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VenteProduit getVenteProduit() { return venteProduit; }
    public void setVenteProduit(VenteProduit venteProduit) { this.venteProduit = venteProduit; }
    public Integer getQuantityReturned() { return quantityReturned; }
    public void setQuantityReturned(Integer quantityReturned) { this.quantityReturned = quantityReturned; }
    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
}
