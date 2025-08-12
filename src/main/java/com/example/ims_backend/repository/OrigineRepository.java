package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Origine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrigineRepository extends JpaRepository<Origine, Long> {

    Page<Origine> findByNomContainingIgnoreCase(String nom, Pageable pageable);
    Page<Origine> findByFournisseurIceContainingIgnoreCase(String ice, Pageable pageable);
}