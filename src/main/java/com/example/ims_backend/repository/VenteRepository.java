package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    // Find ventes by client name (partial, case-insensitive)
    List<Vente> findByClient_NomContainingIgnoreCase(String nom);
}
