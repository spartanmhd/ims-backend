package com.example.ims_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Vente entity represents a sale order.
 */
@Entity
@Table(name = "vente")
public class Vente {

    public enum Status {
        PAYE("payé"),
        NON_PAYE("non payé");
        private final String label;
        Status(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id_client")
    private Client client;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @Column(name = "mode_paiement")
    private String modePaiement; // ex: "espèce", "virement", etc.

    @Column(name = "numero_cheque")
    private String numeroCheque; // nullable; set only if mode_paiement == "cheque/chèque"

    @Column(name = "avance", precision = 19, scale = 2)
    private BigDecimal avance; // nullable unless mode_paiement == "espèce"

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenteProduit> produits = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
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
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<VenteProduit> getProduits() { return produits; }
    public void setProduits(List<VenteProduit> produits) { this.produits = produits; }
}
