package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Origine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrigineRepository extends JpaRepository<Origine, Integer> {
    Optional<Origine> findByNom(String nom);

    Page<Origine> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    Page<Origine> findByFournisseurIceContainingIgnoreCase(String ice, Pageable pageable);
    Page<Origine> findByFournisseurNameContainingIgnoreCase(String name, Pageable pageable);
}