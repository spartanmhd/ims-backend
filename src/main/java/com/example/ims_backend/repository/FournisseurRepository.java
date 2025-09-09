package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    Page<Fournisseur> findByNameContainingIgnoreCase(String name, Pageable pageable);
}