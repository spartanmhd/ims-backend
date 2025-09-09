package com.example.ims_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "inventaire")
public class Inventaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inventaire;

    private String type_stock;
    private Integer id_reference;
    private BigDecimal quantite_reelle;
    private BigDecimal quantite_systeme;
    private String reference_name;

    // Getters and setters
    public Integer getId_inventaire() { return id_inventaire; }
    public void setId_inventaire(Integer id_inventaire) { this.id_inventaire = id_inventaire; }

    public String getType_stock() { return type_stock; }
    public void setType_stock(String type_stock) { this.type_stock = type_stock; }

    public Integer getId_reference() { return id_reference; }
    public void setId_reference(Integer id_reference) { this.id_reference = id_reference; }

    public BigDecimal getQuantite_reelle() { return quantite_reelle; }
    public void setQuantite_reelle(BigDecimal quantite_reelle) { this.quantite_reelle = quantite_reelle; }

    public BigDecimal getQuantite_systeme() { return quantite_systeme; }
    public void setQuantite_systeme(BigDecimal quantite_systeme) { this.quantite_systeme = quantite_systeme; }

    public String getReference_name() { return reference_name; }
    public void setReference_name(String reference_name) { this.reference_name = reference_name; }
}
